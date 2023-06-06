<template>
    <v-container>
        Selecciona una página
        <br>
        <v-btn @click="onClickCerrarSesion">Cerrar sesión</v-btn>
    </v-container>
</template>

<script>
export default {
    name: "Principal",
    props: {},
    data() {
        return {};
    },
    created() {
        if (!this.$session.id() && !this.$session.has("user")) {
            this.$router.push({ name: "Login" })
        }
    },
    mounted() {},
    computed: {},
    watch: {},
    methods: {
        onClickCerrarSesion(){
            this.$session.destroy();
            this.$router.push({ name: "Login" })
        }
    },
};
</script>

<style></style>