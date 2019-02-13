<template>
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <el-menu mode="vertical" :show-timeout="200" :default-active="$route.path" :collapse="isCollapse" background-color="#304156" text-color="#bfcbd9" active-text-color="#409EFF">
      <sidebar-item v-for="route in routes" :key="route.name" :item="route" :base-path="route.path"></sidebar-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from "vuex";
import SidebarItem from "./SidebarItem";

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters(["sidebar", "menuList"]),
    routes() {
      //通过menulist遍历routes是否显示
      var routes_bak = JSON.parse(JSON.stringify(this.$router.options.routes));
      return this.createMenuByAuth(routes_bak);
    },
    isCollapse() {
      return !this.sidebar.opened;
    }
  },
  created() {
    // console.log(this.$router.options.routes);
  },
  methods: {
    createMenuByAuth(routesList) {
      //通过权限下的菜单生成对应的菜单对象
      var new_routes = [];
      if (routesList) {
        for (var i = 0; i < routesList.length; i++) {
          if (!routesList[i].hidden) {
            if (this.menuList.includes(routesList[i].path)) {
              new_routes.push(routesList[i]);
            } else {
              var children = this.createMenuByAuth(routesList[i].children);
              if (children && children.length > 0) {
                routesList[i].children = children;
                new_routes.push(routesList[i]);
              }
            }
          }
        }
      }
      return new_routes;
    }
  }
};
</script>
