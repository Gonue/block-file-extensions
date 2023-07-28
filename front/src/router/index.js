import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import FixedExtensionView from '../views/FixedExtensionView.vue'
import FileView from '../views/FileView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/fixedExtension',
    name: 'fixedExtension',
    component: FixedExtensionView
  },
  {
    path: '/file',
    name: 'file',
    component: FileView
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
