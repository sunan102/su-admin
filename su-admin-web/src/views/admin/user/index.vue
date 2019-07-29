<template>
    <div class="app-container calendar-list-container">
        <div class="filter-container" :inline="true">
            <el-input style="width: 200px;" class="filter-item" placeholder="用户名" v-model="listQuery.username">
            </el-input>
            <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search"
                       @click="handleFilter">搜索
            </el-button>
            <el-button class="filter-item" style="margin-left: 10px;" @click="handleReset">重置</el-button>
            <el-button class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus"
                       @click="handleCreate">添加
            </el-button>
        </div>

        <el-table :data="table.list" v-loading="table.loading" border style="width: 100%">
            <el-table-column label="序号" width="50">
                <template scope="scope">
                    <span>{{scope.$index+(listQuery.nowPage - 1) * listQuery.pageSize + 1}} </span>
                </template>
            </el-table-column>
            <el-table-column prop="username" label="用户名" ></el-table-column>
            <el-table-column label="状态" width="80">
                <template slot-scope="scope">
                    <el-tag v-bind:type="scope.row.status==0?'success':'danger'" size="small">{{scope.row.statusStr}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createDateStr" label="创建时间" width="120"></el-table-column>
            <el-table-column label="操作" width="270" align="center">
                <template slot-scope="scope">
                    <el-button size="small" type="success" @click="handleEdit(scope.row)">编辑
                    </el-button>
                    <el-button v-if="scope.row.status==0" size="small" type="danger"
                               @click="handleDisable(scope.row.adminUserId)">禁用
                    </el-button>
                    <el-button v-if="scope.row.status==1" size="small" type="warning"
                               @click="handleEnable(scope.row.adminUserId)">启用
                    </el-button>
                    <el-button size="small" @click="handleResetPwd(scope.row)">重置密码
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <my-page :size-change="handleSizeChange" :current-change="handleCurrentChange" :current-page="listQuery.nowPage"
                 :total="table.totalElement">
        </my-page>

        <el-dialog :title="saveForm.saveType=='create'?'创建':'编辑'" :visible.sync="saveForm.visible"
                   @close="handleCloseSaveForm">
            <el-form v-loading="saveForm.loading" ref="saveForm" :rules="saveForm.rules" :model="saveForm.entity"
                     label-width="82px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="saveForm.entity.username" :disabled="saveForm.saveType!='create'"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" v-if="saveForm.saveType=='create'">
                    <el-input type="password" v-model="saveForm.entity.password"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-select style="width:100%" v-model="saveForm.entity.status" placeholder="请选择">
                        <el-option v-for="item in saveForm.statusList" :key="item.value" :label="item.text"
                                   :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="saveForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="clickSaveBtn" :loading="saveForm.loading">保 存</el-button>
      </span>
        </el-dialog>

        <!-- 重置密码 -->
        <el-dialog title="重置密码" :visible.sync="resetPwdForm.visible" @close="handleCloseResetPwdForm">
            <el-form v-loading="resetPwdForm.loading" ref="resetPwdForm" :rules="resetPwdForm.rules"
                     :model="resetPwdForm.entity" label-width="82px">
                <el-form-item label="重置密码" prop="password">
                    <el-input type="password" v-model="resetPwdForm.entity.password"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="resetPwdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="clickResetPwdSaveBtn" :loading="resetPwdForm.loading">保 存</el-button>
      </span>
        </el-dialog>

    </div>
</template>

<script>
    import {
        getList,
        save,
        getDetail,
        disable,
        enable,
        resetPwd
    } from "@/api/admin/user";
    import {getAdminUserStatus} from "@/api/static";
    import {notEmpty} from "@/utils/validate.js";
    import MyPage from "@/components/MyPage";

    export default {
        components: {
            MyPage
        },
        computed: {},
        data() {
            return {
                listQuery: {},
                table: {
                    list: null,
                    totalElement: null,
                    loading: false,
                },
                saveForm: {
                    entity: {},
                    saveType: "create",
                    loading: false,
                    visible: false,
                    statusList: [],
                    rules: {
                        username: [{required: true, message: "请输入用户名", trigger: "blur"},
                            {validator: notEmpty, trigger: "blur"}
                        ],
                        password: [{required: true, message: "请输入密码", trigger: "blur"}],
                        status: [{required: true, message: "请选择状态", trigger: "change"}]
                    }
                },
                resetPwdForm: {
                    entity: {},
                    loading: false,
                    visible: false,
                    rules: {
                        password: [{required: true, message: "请输入密码", trigger: "blur"}]
                    }
                }
            };
        },
        created() {
            this.getAdminUserStatus();
        },
        methods: {
            getList() {
                this.listLoading = true;
                getList(this.listQuery).then(response => {
                    this.table.list = response.data.list;
                    this.table.totalElement = response.data.totalElement;
                    this.table.loading = false;
                });
            },
            handleReset() {
                this.listQuery = {
                    username: "",
                    nowPage: null,
                    pageSize: null
                };
                this.handleFilter();
            },
            handleFilter() {
                this.listQuery.nowPage = 1;
                this.getList();
            },
            handleCreate() {
                this.saveForm.saveType = "create";
                this.resetSaveObj();
                this.saveForm.visible = true;
            },
            handleSizeChange(val) {
                this.listQuery.nowPage = 1;
                this.listQuery.pageSize = val;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listQuery.nowPage = val;
                this.getList();
            },
            resetSaveObj() {
                this.saveForm.entity = {
                };
            },
            clickSaveBtn() {
                this.$refs["saveForm"].validate(valid => {
                    if (valid) {
                        this.saveForm.loading = true;
                        save(this.saveForm.entity).then(response => {
                            this.saveForm.loading = false;
                            this.getList();
                            this.saveForm.visible = response.flag != 0;
                        });
                    } else {
                        return false;
                    }
                });
            },
            handleCloseSaveForm() {
                this.saveForm.loading = false;
                this.$refs.saveForm.resetFields();
            },
            handleEdit(obj) {
                this.saveForm.saveType = "edit";
                this.resetSaveObj();
                this.saveForm.loading = true;
                this.saveForm.visible = true;
                getDetail(obj.adminUserId).then(response => {
                    this.saveForm.entity = response.data;
                    this.saveForm.loading = false;
                    this.saveForm.entity.status = response.data.status + "";
                });
            },
            handleDisable(id) {
                //禁用按钮
                this.$confirm("是否确认禁用该用户?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        disable(id).then(response => {
                            this.getList();
                        });
                    })
                    .catch(() => {
                    });
            },
            handleEnable(id) {
                //启用按钮
                this.$confirm("是否确认启用该用户?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        enable(id).then(response => {
                            this.getList();
                        });
                    })
                    .catch(() => {
                    });
            },
            getAdminUserStatus() {
                getAdminUserStatus().then(response => {
                    this.saveForm.statusList = response;
                });
            },
            handleResetPwd(obj) {
                this.resetPwdForm.visible = true;
                this.resetPwdForm.entity.adminUserId = obj.adminUserId;
            },
            handleCloseResetPwdForm() {
                this.$refs.resetPwdForm.resetFields();
            },
            clickResetPwdSaveBtn() {
                this.$refs["resetPwdForm"].validate(valid => {
                    if (valid) {
                        this.resetPwdForm.loading = true;
                        resetPwd(this.resetPwdForm.entity).then(response => {
                            this.resetPwdForm.loading = false;
                            this.resetPwdForm.visible = response.flag != 0;
                        });
                    } else {
                        return false;
                    }
                });
            }
        }
    };
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
