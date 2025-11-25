import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)

// 마운트 시점 변경 (라우터의 초기 탐색 후)
// -> Vue 라우터가 초기 작업을 완료한 시점으로 변경한다.
// 관련한 라우터 기능이 중복되지 않도록
router.isReady().then(() => {
    app.mount('#app')
})

