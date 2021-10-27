import Vue from "vue";
import App from "./App.vue";
import "./assets/css/tailwind.css";
import VeeValidate from "vee-validate";
import titleMixin from './mixins/titleMixin';

Vue.config.productionTip = false;
Vue.use(VeeValidate);
Vue.mixin(titleMixin);

new Vue({
  render: (h) => h(App),
}).$mount("#app");