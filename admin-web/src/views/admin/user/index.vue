<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container" :inline="true">
      <el-input style="width: 200px;" class="filter-item" placeholder="用户名" v-model="listQuery.username">
      </el-input>
      <el-select placeholder="单位名称" filterable v-model="listQuery.companyId">
        <el-option v-for="item in saveForm.companySelectList" :key="item.value" :label="item.text" :value="item.value">
        </el-option>
      </el-select>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleReset">重置</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="success" icon="el-icon-plus" @click="handleCreate">添加</el-button>
    </div>

    <el-table :data="list" v-loading="listLoading" border style="width: 100%">
      <el-table-column label="序号" width="50">
        <template scope="scope">
          <span>{{scope.$index+(listQuery.nowPage - 1) * listQuery.pageSize + 1}} </span>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="300"></el-table-column>
      <el-table-column prop="mobile" label="手机号" width="200"></el-table-column>
      <el-table-column prop="realName" label="真实姓名"></el-table-column>
      <el-table-column prop="roleName" label="所属角色"></el-table-column>
      <el-table-column label="性别" width="50">
        <template slot-scope="scope">
          {{scope.row.sex==0?'男':'女'}}
        </template>
      </el-table-column>
      <el-table-column prop="jobNo" label="工号"></el-table-column>
      <el-table-column prop="companyName" label="所属单位"></el-table-column>
      <el-table-column label="操作" width="270" align="center">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-button v-if="scope.row.status==0" size="small" type="danger" @click="handleDisable(scope.row.id)">禁用
          </el-button>
          <el-button v-if="scope.row.status==1" size="small" type="warning" @click="handleEnable(scope.row.id)">启用
          </el-button>
          <el-button size="small" @click="handleUpdatePwd(scope.row)">重置密码
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <my-page :size-change="handleSizeChange" :current-change="handleCurrentChange" :current-page="listQuery.nowPage" :total="totalElement">
    </my-page>

    <el-dialog v-dialogDrag :title="saveForm.saveType=='create'?'创建':'编辑'" :visible.sync="saveForm.visible" @close="handleCloseSaveForm">
      <el-form v-loading="saveForm.loading" ref="saveForm" :rules="saveForm.rules" :model="saveForm.entity" label-width="82px">
        <el-form-item label="所属单位" prop="companyId">
          <el-select style="width:100%" filterable :loading="saveForm.companySelectListLoading" v-model="saveForm.entity.companyId" placeholder="请选择">
            <el-option v-for="item in saveForm.companySelectList" :key="item.value" :label="item.text" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="saveForm.entity.realName" @input="handleUsernameChange"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="saveForm.entity.username" :disabled="saveForm.saveType!='create'"></el-input>
        </el-form-item>
        <el-form-item label="工号" prop="jobNo">
          <el-input v-model="saveForm.entity.jobNo"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="saveForm.saveType=='create'">
          <el-input type="password" v-model="saveForm.entity.password"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="saveForm.entity.sex">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="saveForm.entity.mobile"></el-input>
        </el-form-item>
        <el-form-item label="所属角色" prop="roleId">
          <el-select style="width:100%" :loading="saveForm.roleSelectListLoading" v-model="saveForm.entity.roleId" placeholder="请选择">
            <el-option v-for="item in saveForm.roleSelectList" :key="item.value" :label="item.text" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select style="width:100%" v-model="saveForm.entity.status" placeholder="请选择">
            <el-option v-for="item in saveForm.statusList" :key="item.value" :label="item.text" :value="item.value">
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
    <el-dialog v-dialogDrag title="重置密码" :visible.sync="updatePwdForm.visible" @close="handleCloseUpdateForm">
      <el-form v-loading="updatePwdForm.loading" ref="updatePwdForm" :rules="updatePwdForm.rules" :model="updatePwdForm.entity" label-width="82px">
        <el-form-item label="重置密码" prop="password">
          <el-input type="password" v-model="updatePwdForm.entity.password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updatePwdForm.visible = false">取 消</el-button>
        <el-button type="primary" @click="clickUpdatePwdSaveBtn" :loading="updatePwdForm.loading">保 存</el-button>
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
  updatePwd
} from "@/api/admin/user";
import { getAllCompany } from "@/api/admin/company";
import { getAllRole } from "@/api/admin/role";
import { getAdminUserStatus } from "@/api/static";
import { ConvertPinyin } from "@/directives/vue-py.js";
import { notEmpty } from "@/utils/validate.js";
import MyPage from "@/components/MyPage";

export default {
  components: {
    MyPage
  },
  computed: {},
  data() {
    return {
      listQuery: {},
      list: null,
      totalElement: null,
      listLoading: false,
      saveForm: {
        entity: {},
        saveType: "create",
        loading: false,
        visible: false,
        companySelectListLoading: false,
        companySelectList: [],
        roleSelectListLoading: false,
        roleSelectList: [],
        statusList: [],
        rules: {
          companyId: [
            { required: true, message: "请选择所属单位", trigger: "change" }
          ],
          username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
          realName: [
            { required: true, message: "请输入真实姓名", trigger: "blur" },
            { validator: notEmpty, trigger: "blur" }
          ],
          password: [{ required: true, message: "请输入密码", trigger: "blur" }],
          roleId: [{ required: true, message: "请选择角色", trigger: "change" }],
          status: [{ required: true, message: "请选择状态", trigger: "change" }]
        }
      },
      updatePwdForm: {
        entity: {},
        loading: false,
        visible: false,
        rules: {
          password: [{ required: true, message: "请输入密码", trigger: "blur" }]
        }
      }
    };
  },
  created() {
    this.getAllCompany();
    this.getAllRole();
    this.getAdminUserStatus();
  },
  methods: {
    getList() {
      this.listLoading = true;
      getList(this.listQuery).then(response => {
        this.list = response.data.list;
        this.totalElement = response.data.totalElement;
        this.listLoading = false;
      });
    },
    handleReset() {
      this.listQuery = {
        username: "",
        companyId: "",
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
        sex: 0
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
      this.saveForm.entity.id = obj.id;
      this.resetSaveObj();
      this.saveForm.loading = true;
      this.saveForm.visible = true;
      getDetail(obj.id).then(response => {
        this.saveForm.entity = response.data;
        this.saveForm.loading = false;
        this.saveForm.entity.status = response.data.status + "";
      });
    },
    getAllCompany() {
      //公司下拉菜单获取数据
      this.saveForm.companySelectListLoading = true;
      getAllCompany().then(response => {
        this.saveForm.companySelectListLoading = false;
        if (response.data == null) {
          this.saveForm.companySelectList = [];
        } else {
          this.saveForm.companySelectList = response.data;
        }
      });
    },
    getAllRole() {
      //角色下拉菜单获取数据
      this.saveForm.roleSelectListLoading = true;
      getAllRole().then(response => {
        this.saveForm.roleSelectListLoading = false;
        if (response.data == null) {
          this.saveForm.roleSelectList = [];
        } else {
          this.saveForm.roleSelectList = response.data;
        }
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
        .catch(() => {});
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
        .catch(() => {});
    },
    getAdminUserStatus() {
      getAdminUserStatus().then(response => {
        this.saveForm.statusList = response.data;
      });
    },
    handleUpdatePwd(obj) {
      this.updatePwdForm.visible = true;
      this.updatePwdForm.entity.uid = obj.id;
    },
    handleCloseUpdateForm() {
      this.$refs.updatePwdForm.resetFields();
    },
    clickUpdatePwdSaveBtn() {
      this.$refs["updatePwdForm"].validate(valid => {
        if (valid) {
          this.updatePwdForm.loading = true;
          updatePwd(this.updatePwdForm.entity).then(response => {
            this.updatePwdForm.loading = false;
            this.updatePwdForm.visible = response.flag != 0;
          });
        } else {
          return false;
        }
      });
    },
    handleUsernameChange() {
      console.log(111);
      //用户名输入事件
      this.saveForm.entity.username = ConvertPinyin(
        this.saveForm.entity.realName
      );
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
