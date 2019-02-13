<template>
  <div>
    <el-pagination
      v-if="total>0"
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="nowPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  props: {
    total: Number,
    nowPage: Number,
    sizeChange: {
      type: Function
    },
    currentChange: {
      type: Function
    }
  },
  created(){
    this.sizeChange(10);
  }
};
</script>
