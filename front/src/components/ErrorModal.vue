<!-- ErrorModal.vue -->
<template>
    <div v-if="modelValue" class="modal d-block" @click="close" @keyup.enter="close" tabindex="0">
        <div class="modal-dialog" @click.stop>
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">{{ title }}</h5>
                    <button type="button" class="btn-close" @click="close"></button>
                </div>
                <div class="modal-body">
                    <p>{{ message }}</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" @click="close">Close</button>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    props: ['modelValue', 'title', 'message'],
    emits: ['update:modelValue'],
    methods: {
        close() {
            this.$emit('update:modelValue', false)
        },
    },
}
</script>
  