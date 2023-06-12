<template>
    <v-container>
            <Menu />
        <v-col>
        </v-col>
        <v-col>
            <v-img class="mx-auto" contain max-height="450" max-width="550"
                :src="require('../assets/titulo_sag.png')"></v-img>
        </v-col>
    </v-container>
</template>

<script>
import Menu from "@/components/Menu.vue";
export default {
    name: "Principal",
    components: {
        Menu,
    },
    props: {},
    data() {
        return {};
    },
    created() {
        if (!this.$session.id() && !this.$session.has("user")) {
            this.$router.push({ name: "Login" })
        }
    },
    mounted() { },
    computed: {},
    watch: {},
    methods: {
        onClickCerrarSesion() {
            this.$session.destroy();
            this.$router.push({ name: "Login" })
        }
    },
};
</script>

<style></style>