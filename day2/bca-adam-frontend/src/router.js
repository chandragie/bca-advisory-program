// @ts-nocheck
import Vue from "vue";
import Router from "vue-router";
import Login from "./components/login/Login.vue";
import Signup from "./components/login/Signup.vue"
import TodoList from "./components/todo/TodoList.vue"

Vue.use(Router);

export const router = new Router({
  mode: "history",
  routes: [{
      path: "/",
      component: TodoList
    },
    {
      path: "/login",
      component: Login
    },
    {
      path: "/signup",
      component: Signup,
    },
    {
      path: "/todo",
      //lazy loaded
      component: () => import("./components/todo/TodoList"),
    },
    {
      path: "/todo/add",
      //lazy loaded
      component: () => import("./components/todo/AddTodo"),
    },
  ],
});

router.beforeResolve((to, from, next) => {
  const publicPages = ['/login', '/signup'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('token');
  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    next();
  }
});