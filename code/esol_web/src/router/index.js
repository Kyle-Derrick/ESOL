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
    redirect: "/home/list",
    children: [
      {
        path: "/home/list",
        name: "list",
        component: () => import("../views/children/HomeList.vue")
      },
      {
        path: "/home/my",
        name: "my",
        component: () => import("../views/children/HomeMy.vue")
      }
    ]
  },
  {
    path: "/exam",
    name: "exam",
    component: () => import("../views/Exam.vue"),
    redirect: "/exam/test",
    children: [
      {
        path: "/exam/test",
        name: "test",
        component: () => import("../views/children/ExamTest.vue")
      },
      {
        path: "/exam/result",
        name: "result",
        component: () => import("../views/children/ExamResult.vue")
      },
      {
        path: "/exam/answer",
        name: "answer",
        component: () => import("../views/children/ExamResultAnswer.vue")
      },
      {
        path: "/exam/history",
        name: "history",
        component: () => import("../views/children/ExamHistory.vue")
      },
      {
        path: "/exam/result/history",
        name: "resultHistory",
        component: () => import("../views/children/ExamResultHistory.vue")
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
  scrollBehavior(to,from,savedPosition){
    if(savedPosition){
      return savedPosition;
    }else{
      return {x:0,y:0}
    }
  }
});

export default router;
