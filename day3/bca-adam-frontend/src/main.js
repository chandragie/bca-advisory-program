import Vue from "vue";
import App from "./App.vue";
import "./assets/css/tailwind.css";
import {
  router
} from "./router";
import VeeValidate from "vee-validate";
import store from './store';
import titleMixin from './mixins/titleMixin';

Vue.config.productionTip = false;
Vue.use(VeeValidate);
Vue.mixin(titleMixin);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");