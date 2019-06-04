<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container">
            <!--<el-button-group>-->
            <!--<el-button type="primary"-->
            <!--@click="handlerAdd">添加-->
            <!--</el-button>-->
            <!--<el-button type="primary"-->
            <!--@click="handlerEdit">编辑-->
            <!--</el-button>-->
            <!--<el-button type="primary"-->
            <!--@click="handleDelete">删除-->
            <!--</el-button>-->
            <!--</el-button-group>-->
        </div>

        <el-row>
            <el-col :span="8">
                <el-tree :data="menuTreeData" node-key="id" @node-click="clickTreeNode" :expand-on-click-node="false"
                         ref="tree">
                    <span slot-scope="{ node, data }">
                        <span>{{ node.label }}</span>
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
                        <el-form-item label="名称" prop="menuName">
                            <el-input v-model="form.menuName"></el-input>
                        </el-form-item>
                        <el-form-item label="url" prop="url">
                            <el-input v-model="form.url"></el-input>
                        </el-form-item>
                        <el-form-item label="标识" prop="mark">
                            <el-input v-model="form.mark"></el-input>
                        </el-form-item>
                        <el-form-item label="排序" prop="num">
                            <el-input v-model="form.num"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="saveForm()" v-if="selectNode.type==1">添加功能
                            </el-button>
                            <el-button type="primary" @click="saveForm()" v-if="selectNode.type==2">修改
                            </el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {getList, save} from "@/api/admin/menu";
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
                    url: [
                        {required: true, message: "必填项", trigger: "change"}
                    ],
                    mark: [
                        {required: true, message: "必填项", trigger: "change"}
                    ],
                    num: [
                        {required: true, message: "必填项", trigger: "change"}
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
                    this.menuTreeData = createMenuTree(this.$router.options.routes, this.menuList);
                });
            },
            initForm() {
                this.form = {};
                this.$refs["form"].resetFields();
            },
            clickTreeNode(data, node) {
                this.initForm();
                this.selectNode = data;
                console.log(data, node)
                if (data.type == 1) {
                    this.form.pid = data.id;
                    this.form.pname = data.label;
                } else {
                    this.form = JSON.parse(JSON.stringify(data));
                    this.form.pid = node.parent.data.id;
                    this.form.pname = node.parent.data.label;
                }
                this.form.type = data.type;
            },
            saveForm() {
                var _this = this;
                this.$refs["form"].validate(valid => {
                    if (valid) {
                        _this.formLoading = true;
                        save(_this.form).then(response => {
                            _this.formLoading = false;
                            if (_this.form.type == 1) {
                                _this.form.adminMenuId = response.data;
                                _this.form.id = response.data;
                                _this.form.label = _this.form.menuName;
                                _this.addTreeNode(_this.menuTreeData[0], _this.form);
                            } else {
                                _this.updateTreeNode(_this.menuTreeData[0], _this.form);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            updateTreeNode(tree, node) {
                if (tree.children) {
                    for (var i = 0; i < tree.children.length; i++) {
                        if (tree.children[i].id == node.pid) {
                            var children = JSON.parse(JSON.stringify(tree.children[i].children));
                            if (children) {
                                for (var j = 0; j < children.length; j++) {
                                    if (children[j].id == node.id) {
                                        children[j] = JSON.parse(JSON.stringify(node));
                                    }
                                }
                            }
                            this.$refs.tree.updateKeyChildren(node.pid, children);
                            break;
                        }
                        this.updateTreeNode(tree.children[i], node);
                    }
                }
            },
            addTreeNode(tree, node) {
                if (tree.children) {
                    for (var i = 0; i < tree.children.length; i++) {
                        if (tree.children[i].id == node.pid) {
                            var children = JSON.parse(JSON.stringify(tree.children[i].children));
                            if (!children) {
                                children = [];
                            }
                            node.type = 2;
                            children.push(JSON.parse(JSON.stringify(node)));
                            children = children.sort(function (a, b) {
                                return a.num - b.num;
                            });
                            console.log(children)
                            this.$refs.tree.updateKeyChildren(node.pid, children);
                            break;
                        }
                        this.updateTreeNode(tree.children[i], node);
                    }
                }
            }
        }
    };
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
