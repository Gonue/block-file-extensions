<template>
    <div class="container mt-5">
        <div class="row">
            <div class="col">
                <h2>파일 업로드</h2>
                <form @submit.prevent="uploadFile">
                    <div class="mb-3">
                        <input type="file" class="form-control" id="fileUpload" ref="fileInput" @change="selectFile">
                    </div>
                    <button type="submit" class="btn btn-primary" :disabled="!selectedFile">업로드</button>
                </form>
            </div>
        </div>

        <div class="row mt-5">
            <div class="col">
                <h2>파일 목록</h2>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">파일 이름</th>
                            <th scope="col">확장자</th>
                            <th scope="col">다운로드</th>
                            <th scope="col">삭제</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="file in files" :key="file.id">
                            <td>{{ file.fileName }}</td>
                            <td>{{ file.extension }}</td>
                            <td><button class="btn btn-secondary" @click="downloadFile(file.id)">다운로드</button></td>
                            <td><button class="btn btn-danger" @click="deleteFile(file.id)">삭제</button></td>
                        </tr>
                    </tbody>
                </table>
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
            files: [],
            selectedFile: null,
            errorOccurred: false,
            errorMessage: '',

        }
    },
    methods: {
        async getFiles() {
            try {
                const response = await server.get('/api/v1/upload-file')
                this.files = response.data.result
            } catch (error) {
                console.error(error)
            }
        },
        selectFile(event) {
            this.selectedFile = event.target.files[0]
        },
        async uploadFile() {
            try {
                const formData = new FormData()
                formData.append('file', this.selectedFile)

                await server.post('/api/v1/upload-file', formData)
                this.getFiles()

                this.$refs.fileInput.value = ''
                this.selectedFile = null
            } catch (error) {
                if (error.response && error.response.status === 400) {
                    this.errorOccurred = true;
                    this.errorMessage = '차단된 확장자 입니다.'
                }
                console.error(error)
            }
        },
        async downloadFile(id) {
            try {
                const file = this.files.find(file => file.id === id)
                if (!file) {
                    throw new Error('File not found')
                }

                const response = await server.get(`/api/v1/upload-file/${id}`, { responseType: 'blob' })
                const url = window.URL.createObjectURL(new Blob([response.data]))
                const link = document.createElement('a')
                link.href = url
                link.setAttribute('download', file.fileName)
                document.body.appendChild(link)
                link.click()
            } catch (error) {
                console.error(error)
            }
        },
        async deleteFile(id) {
            try {
                await server.delete(`/api/v1/upload-file/${id}`)
                this.getFiles()
            } catch (error) {
                console.error(error)
            }
        }
    },
    created() {
        this.getFiles()
    }
}
</script>

<style scoped></style>
