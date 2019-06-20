<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container">
            <el-button type="success" size="small" @click="handleAddRoot" round>添加根菜单</el-button>
        </div>

        <el-row>
            <el-col :span="8">
                <el-tree :data="menuTreeData" node-key="id" :expand-on-click-node="false"
                         ref="tree">
                    <span slot-scope="{ node, data }">
                        <i class="el-icon-folder" v-if="data.type==1"></i>
                        <i class="el-icon-tickets" v-if="data.type==2"></i>
                        <span>{{ node.label }}</span>
                        <span style="margin-left: 20px;">
                            <el-tooltip class="item" effect="dark" content="查看信息" placement="top">
                                <el-link icon="el-icon-view" @click="clickTreeNode(data, node)"
                                         :underline="false"></el-link>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="添加子菜单" placement="top">
                                <el-link icon="el-icon-circle-plus" @click="handleAddChildMenu(data,node)"
                                         style="margin-left: 5px;" :underline="false" v-if="data.type==1"></el-link>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="添加功能" placement="top">
                                <el-link icon="el-icon-circle-plus-outline" @click="handleAddAction(data,node)"
                                         style="margin-left: 5px;" :underline="false" v-if="data.type==1"></el-link>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="删除" placement="top">
                                <el-link icon="el-icon-delete" @click="handleDelete(data)"
                                         style="margin-left: 5px;" :underline="false"></el-link>
                            </el-tooltip>
                        </span>
                    </span>
                </el-tree>
            </el-col>
            <el-col :span="16">
                <el-card class="box-card">
                    <el-form label-width="80px" v-loading="formLoading" :rules="formRules"
                             :model="form"
                             ref="form">
                        <el-form-item label="父级节点">
                            <el-input v-model="form.pname" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="显示名称" prop="menuName">
                            <el-input v-model="form.menuName"></el-input>
                        </el-form-item>
                        <el-form-item label="name" prop="vueName" v-if="form.type==1">
                            <el-input v-model="form.vueName"></el-input>
                        </el-form-item>
                        <el-form-item label="url" prop="url">
                            <el-input v-model="form.url"></el-input>
                        </el-form-item>
                        <el-form-item label="page" prop="page" v-if="form.type==1">
                            <el-input v-model="form.page"></el-input>
                        </el-form-item>
                        <el-form-item label="icon" prop="icon" v-if="form.type==1">
                            <el-input v-model="form.icon"></el-input>
                        </el-form-item>
                        <el-form-item label="标识" prop="mark" v-if="form.type==2">
                            <el-input v-model="form.mark"></el-input>
                        </el-form-item>
                        <el-form-item label="排序" prop="num">
                            <el-input v-model.number="form.num"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="saveForm('add')" v-if="form.type==1">保存菜单
                            </el-button>
                            <el-button type="primary" @click="saveForm('add')" v-if="form.type==2">保存功能
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script type="text/jsx">
    import {getList, save, del} from "@/api/admin/menu";
    import {createMenuTree} from "@/utils/index";

    export default {
        components: {},
        computed: {},
        data() {
            return {
                listLoading: false,
                formLoading: false,
                menuTreeData: [],
                menuList: [],
                form: {},
                formRules: {
                    menuName: [
                        {required: true, message: "必填项", trigger: "change"}
                    ],
                    vueName: [
                        {required: true, message: "必填项", trigger: "change"}
                    ],
                    url: [
                        {required: true, message: "必填项", trigger: "change"}
                    ],
                    mark: [
                        {required: true, message: "必填项", trigger: "change"}
                    ],
                    num: [
                        {required: true, message: "必填项"},
                        {type: 'number', message: '必须为数字值'}
                    ]
                },
                selectNode: {}
            };
        },
        created() {
            this.getList();
        },
        methods: {
            getList() {
                this.listLoading = true;
                getList(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.menuList = response.data;
                    this.menuTreeData = createMenuTree(this.menuList, "0");
                });
            },
            initForm() {
                this.form = {};
                this.$refs["form"].resetFields();
            },
            handleAddRoot() {
                //添加根菜单
                this.initForm();
                this.form.pname = "根菜单";
                this.form.pid = "0";
                this.form.type = 1;
            },
            clickTreeNode(data, node) {
                //点击查看树节点
                this.initForm();
                this.form = JSON.parse(JSON.stringify(data));
                this.form.pname = data.pid == "0" ? "根菜单" : node.parent.data.label;
            },
            handleAddChildMenu(data, node) {
                //点击添加子菜单
                this.initForm();
                this.form.pid = data.id;
                this.form.pname = data.menuName;
                this.form.type = 1;
            },
            handleAddAction(data, node) {
                //点击添加功能
                this.initForm();
                this.form.pid = data.id;
                this.form.pname = data.menuName;
                this.form.type = 2;
            },
            saveForm() {
                var _this = this;
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        _this.formLoading = true;
                        save(_this.form).then(response => {
                            _this.formLoading = false;
                            if (!_this.form.adminMenuId) {
                                _this.form.adminMenuId = response.data;
                                _this.form.id = response.data;
                                _this.form.label = _this.form.menuName;
                                _this.addTreeNode(_this.menuTreeData, _this.form, _this.form.pid == "0");
                            } else {
                                _this.form.label = _this.form.menuName;
                                _this.updateTreeNode(_this.menuTreeData, _this.form, _this.form.pid == "0");
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            updateTreeNode(tree, node, is_root) {
                if (is_root) {
                    let tree_new = [];
                    for (let i = 0; i < tree.length; i++) {
                        if (tree[i].id == node.id) {
                            tree_new.push(JSON.parse(JSON.stringify(node)));
                        } else {
                            tree_new.push(JSON.parse(JSON.stringify(tree[i])));
                        }
                        this.$refs.tree.remove(tree[i]);
                    }
                    tree_new = tree_new.sort(function (a, b) {
                        return a.num - b.num;
                    });
                    for (let i = 0; i < tree_new.length; i++) {
                        this.$refs.tree.append(tree_new[i]);
                    }
                } else {
                    if (tree) {
                        for (var i = 0; i < tree.length; i++) {
                            if (tree[i].id == node.pid) {
                                var children = JSON.parse(JSON.stringify(tree[i].children));
                                if (children) {
                                    for (var j = 0; j < children.length; j++) {
                                        if (children[j].id == node.id) {
                                            children[j] = JSON.parse(JSON.stringify(node));
                                        }
                                    }
                                }
                                children = children.sort(function (a, b) {
                                    return a.num - b.num;
                                });
                                this.$refs.tree.updateKeyChildren(node.pid, children);
                                break;
                            }
                            if (tree[i].children && tree[i].children.length > 0) {
                                this.updateTreeNode(tree[i].children, node);
                            }
                        }
                    }
                }
            },
            addTreeNode(tree, node, is_root) {
                //TODO: 添加根菜单存在排序问题
                if (is_root) {
                    let tree_new = [];
                    for (let i = 0; i < tree.length; i++) {
                        tree_new.push(JSON.parse(JSON.stringify(tree[i])));
                        this.$refs.tree.remove(tree[i]);
                    }
                    tree_new.push(JSON.parse(JSON.stringify(node)));
                    tree_new = tree_new.sort(function (a, b) {
                        return a.num - b.num;
                    });
                    console.log(tree, tree_new)
                    for (let i = 0; i < tree_new.length; i++) {
                        this.$refs.tree.append(tree_new[i]);
                    }
                } else {
                    if (tree) {
                        for (var i = 0; i < tree.length; i++) {
                            if (tree[i].id == node.pid) {
                                if (!tree[i].children) {
                                    tree[i].children = [];
                                }
                                var children = JSON.parse(JSON.stringify(tree[i].children));
                                children.push(JSON.parse(JSON.stringify(node)));
                                children = children.sort(function (a, b) {
                                    return a.num - b.num;
                                });
                                this.$refs.tree.updateKeyChildren(node.pid, children);
                                break;
                            }
                            if (tree[i].children && tree[i].children.length > 0) {
                                this.addTreeNode(tree[i].children, node);
                            }
                        }
                    }
                }
            },
            deleteTreeNode(tree, node) {
                this.$refs.tree.remove(node);
            },
            handleDelete(data) {
                this.$confirm("是否确认删除\"" + data.label + "\"?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        this.formLoading = true;
                        del(data.id).then(response => {
                            this.formLoading = false;
                            this.deleteTreeNode(this.menuTreeData, data);
                            this.initForm();
                        });
                    })
                    .catch(() => {
                    });
            }
        }
    };
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
