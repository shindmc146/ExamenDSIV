import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/', component: () => import('@/views/ReportList.vue') },
    {path: '/reports/create', component: () => import('@/views/ReportForm.vue')},
    {path: '/reports/edit/:id', component: () => import('@/views/ReportForm.vue')}
  ],
})

export default router
