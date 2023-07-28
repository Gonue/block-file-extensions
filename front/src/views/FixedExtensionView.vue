<template>
  <div class="container mt-5">
    <div class="row g-3">
      <div class="col-3">
        <h3>고정 확장자</h3>
        <h5>{{ fixedExtensions.length }}/200</h5>
      </div>
      <div class="col">
        <form class="row g-3" @submit.prevent="addFixedExtension">
          <div class="col-auto">
            <input v-model="newFixedExtension" type="text" class="form-control" placeholder="고정확장자 추가">
          </div>
          <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3"> +추가 </button>
          </div>
        </form>
        <div id="fix" class="d-flex flex-wrap">
          <div class="p-1 extension" v-for="extension in fixedExtensions" :key="extension.id">
            {{ extension.extension }}
            <button class="btn btn-secondary btn-sm" @click="deleteFixedExtension(extension.id)">x</button>
          </div>
        </div>
      </div>
    </div>

    <ErrorModal v-model="errorOccurred" title="Error" :message="errorMessage"/>

  </div>
</template>

<script>
import ErrorModal from '../components/ErrorModal.vue'
import axios from 'axios'
const server = axios.create({
  baseURL: process.env.SERVER_URL,
})

export default {
  components: {ErrorModal},
  data() {
    return {
      fixedExtensions: [],
      newFixedExtension: '',
      errorOccurred: false,
      errorMessage: '',
    }
  },
  methods: {
    async getFixedExtensions() {
      try {
        const response = await server.get('/api/v1/fixed-extensions')
        this.fixedExtensions = response.data.result
      } catch (error) {
        console.error(error)
      }
    },
    async addFixedExtension() {
      try {
        const response = await server.post('/api/v1/fixed-extensions', { extension: this.newFixedExtension })
        this.fixedExtensions.push(response.data.result)
        this.newFixedExtension = ''
      } catch (error) {
                      if (error.response && error.response.status === 409) {
          this.errorOccurred = true;
          this.errorMessage = '이미 등록된 확장자 입니다.'
        }
        console.error(error)
      }
    },
    async deleteFixedExtension(id) {
      try {
        await server.delete(`/api/v1/fixed-extensions/${id}`)
        this.fixedExtensions = this.fixedExtensions.filter(extension => extension.id !== id)
      } catch (error) {
        console.error(error)
      }
    }
  },
  created() {
    this.getFixedExtensions()
  }
}
</script>

<style scoped>

.extension {
  flex-basis: 16.66%;
}

#fix {
  border-width: 0.5px;
  border-style: solid;
  border-radius: 8px;
}

</style>