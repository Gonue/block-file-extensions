<template>
  <div class="container mt-5">
    <div class="row g-3">
      <div class="col-3">
        <h3>고정 확장자</h3>
        <h5> {{ fixedExtensions.length }}/200</h5>
      </div>
      <div class="col">
        <div class="row" id="fix">
          <div class="p-1 extension" v-for="extension in fixedExtensions" :key="extension.id">
            <span class="extension-name">{{ extension.extension }}</span>
            <input class="form-check-input" type="checkbox" v-model="extension.active"
              @change="toggleActive(extension)" />
          </div>
        </div>
      </div>
    </div>

    <div class="row g-3 mt-5">
      <div class="col-3">
        <h3>커스텀 확장자</h3>
        <h5>{{ customExtensions.length }}/200</h5>
      </div>
      <div class="col">
        <form class="row g-3" @submit.prevent="addCustomExtension">
          <div class="col-auto">
            <input v-model="newCustomExtension" type="text" class="form-control" placeholder="커스텀확장자 추가">
          </div>
          <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3"> +추가 </button>
          </div>
        </form>
        <div id="fix" class="d-flex flex-wrap">
          <div class="p-1 extension" v-for="extension in customExtensions" :key="extension.id">
            {{ extension.extension }}
            <button class="btn btn-secondary btn-sm" @click="deleteCustomExtension(extension.id)">x</button>
          </div>
        </div>
      </div>
    </div>

    <ErrorModal v-model="errorOccurred" title="Error" :message="errorMessage" />

  </div>
</template>

<script>
import ErrorModal from '../components/ErrorModal.vue'
import axios from 'axios'
const server = axios.create({
  baseURL: process.env.SERVER_URL,
})

export default {
  components: { ErrorModal },
  data() {
    return {
      fixedExtensions: [],
      customExtensions: [],
      newCustomExtension: '',
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
    async toggleActive(extension) {
      try {
        const response = await server.put(`/api/v1/fixed-extensions/${extension.id}`, { isActive: extension.active })
        const index = this.fixedExtensions.findIndex(item => item.id === extension.id)
        this.fixedExtensions[index] = response.data.result
      } catch (error) {
        console.error(error)
      }
    },
    async getCustomExtensions() {
      try {
        const response = await server.get('/api/v1/custom-extensions')
        this.customExtensions = response.data.result
      } catch (error) {
        console.error(error)
      }
    },
    async addCustomExtension() {
      try {
        if (!this.newCustomExtension.trim() || this.newCustomExtension.length > 20) {
          this.errorMessage = '확장자는 공백이나 20자 이상일 수 없습니다.';
          this.errorOccurred = true;
          return;
        }

        const englishAlphabetRegex = /^[A-Za-z]*$/;
        if (!englishAlphabetRegex.test(this.newCustomExtension)) {
          this.errorMessage = '확장자는 영문자로 이루어져 있어야 합니다.';
          this.errorOccurred = true;
          return;
        }
        const response = await server.post('/api/v1/custom-extensions', { extension: this.newCustomExtension })
        this.customExtensions.push(response.data.result)
        this.newCustomExtension = ''
      } catch (error) {
        if (error.response && error.response.status === 409) {
          this.errorOccurred = true;
          this.errorMessage = '이미 등록된 확장자 입니다.'
        }
        console.error(error)
      }
    },
    async deleteCustomExtension(id) {
      try {
        await server.delete(`/api/v1/custom-extensions/${id}`)
        this.customExtensions = this.customExtensions.filter(extension => extension.id !== id)
      } catch (error) {
        console.error(error)
      }
    }
  },
  created() {
    this.getFixedExtensions()
    this.getCustomExtensions()
  }
}
</script>

<style scoped>
#fix {
  border-width: 0.5px;
  border-style: solid;
  border-radius: 8px;
}

.extension {
  flex-basis: 16.66%;
}

.extension-name {
  margin-right: 8px;
}
</style>