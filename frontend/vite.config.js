import {fileURLToPath, URL} from 'node:url'
import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  // 프록시 서버를 설정하지 않으면 백앤드 서버에 요청을 할 수 없기 때문에 설정을 해서 백엔드 서버로 요청을 보낼 수 있도록 우회한다.
  // 프론트(5173) -> 프론트 서버(vite) -> 백엔드 서버(8080)
  server: {
    proxy: {
      "/v1/api": {
        target: "http://localhost:8080",
        changeOrigin: true
      }
    }
  },
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
});