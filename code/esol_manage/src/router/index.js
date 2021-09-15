import Vue from "vue";
import VueRouter from "vue-router";
import Index from "../views/Index.vue";

Vue.use(VueRouter);
/**
 * 前端路由配置
 */
const routes = [
  {
    path: "/",
    component: Index
  },
  {
    path: "/index",
    name: "index",
    redirect: "/"
  },
  {
    path: "/home",
    name: "home",
    component: () => import("../views/Home.vue"),
    redirect: "/home/user",
    children: [
      {
        path: "/home/user",
        name: "user",
        component: () => import("../views/children/user.vue")
      },
      {
        path: "/home/dept",
        name: "dept",
        component: () => import("../views/children/dept.vue")
      },
      {
        path: "/home/question",
        name: "question",
        component: () => import("../views/children/question.vue")
      },
      {
        path: "/home/exam",
        name: "exam",
        component: () => import("../views/children/exam.vue")
      }
    ]
  }

  // {
  //   path: "/about",
  //   name: "about",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/About.vue")
  // }
];

const router = new VueRouter({
  // mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  }
});

export default router;
