<template>
    <bk-select v-bind="dropdownConf"
        :name="name"
        :loading="isLoading"
        :placeholder="isLoading ? $t('editPage.loadingData') : placeholder"
        :value="value"
        :disabled="disabled || isLoading"
        @selected="handleSelect"
        @toggle="toggleVisible"
        @clear="handleClear"
        :popover-options="popoverOptions"
    >
        <bk-option
            v-for="item in list"
            :key="item.id"
            :id="item.id"
            :name="item.name"
        >
        </bk-option>
        <template v-if="mergedOptionsConf.hasAddItem">
            <div slot="extension" class="bk-selector-create-item">
                <a :href="addItemUrl" target="_blank">
                    <i class="devops-icon icon-plus-circle" />
                    {{ mergedOptionsConf.itemText }}
                </a>
            </div>
        </template>
    </bk-select>
</template>

<script>
    import mixins from '../mixins'
    import selectorMixins from '../selectorMixins'
    export default {
        mixins: [mixins, selectorMixins],
        props: {
            placeholder: {
                type: String
            }
        },
        data () {
            return {
                isLoading: false,
                list: this.options
            }
        },
        computed: {
            popoverOptions () {
                return {
                    popperOptions: {
                        modifiers: {
                            preventOverflow: {
                                boundariesElement: 'window'
                            }
                        }
                    }
                }
            },
            dropdownConf () {
                const { searchable, multiple, clearable } = this.mergedOptionsConf
                return {
                    searchable,
                    multiple,
                    clearable
                }
            }
        },
        watch: {
            options (newOptions) {
                this.list = newOptions
            },
            queryParams (newQueryParams, oldQueryParams) {
                if (this.urlParamKeys.some(key => newQueryParams[key] !== oldQueryParams[key])) {
                    this.handleClear()
                }
            }
        },
        created () {
            if (this.hasUrl) {
                this.freshList()
            }
        },
        methods: {
            handleSelect (selected, data) {
                this.handleChange(this.name, selected)
            },
            handleClear () {
                const val = this.dropdownConf.multiple ? [] : ''

                this.handleChange(this.name, val)
            },
            toggleVisible (open) {
                open && this.hasUrl && this.freshList()
            },
            transformList (res) {
                const list = this.getResponseData(res, this.mergedOptionsConf.dataPath)
                return list.map(item => {
                    if (typeof item === 'string') {
                        return {
                            id: item,
                            name: item
                        }
                    }
                    return {
                        ...item,
                        id: item[this.mergedOptionsConf.paramId],
                        name: item[this.mergedOptionsConf.paramName]
                    }
                })
            },
            async freshList () {
                if (this.isLackParam) { // 缺少参数时，选择列表置空
                    this.list = []
                    return
                }
                try {
                    const { atomValue = {}, transformList, $route: { params = {} }, mergedOptionsConf } = this
                    const changeUrl = this.urlParse(mergedOptionsConf.url, {
                        ...params,
                        ...atomValue
                    })
                    this.isLoading = true

                    const res = await this.$ajax.get(changeUrl)

                    this.list = transformList(res)
                    // 添加无权限查看项
                    const valueArray = mergedOptionsConf.multiple && Array.isArray(this.value) ? this.value : [this.value]
                    const listMap = this.list.reduce((listMap, item) => {
                        listMap[item.id] = item
                        return listMap
                    }, {})

                    valueArray.map(value => {
                        if (typeof value !== 'undefined' && value !== '' && !listMap[value]) {
                            this.list.splice(0, 0, {
                                id: value,
                                name: `******（${this.$t('editPage.noPermToView')}）`
                            })
                        }
                    })

                    this.$emit('change', this.list)
                } catch (e) {
                    this.$showTips({
                        message: e.message,
                        theme: 'error'
                    })
                } finally {
                    this.isLoading = false
                }
            }
        }
    }
</script>

<style lang="scss">
    .bk-form .bk-form-content,
    .form-field.bk-form-item {
        position: static;
    }
</style>
