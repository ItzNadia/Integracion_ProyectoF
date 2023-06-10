<template>
    <v-container>
        <v-card>
            <v-card-title>Cosultas Médicas</v-card-title>
            <div>{{ this.$props.idHatoSelec }}</div>
        </v-card>
    </v-container>
</template>

<script>
export default {
    name: "ConsultasMedicas",
    props: ['idHatoSelec'],
    data() {
        return {
        };
    },
    created() {
        console.log("Consultas médicas, idHato: " + this.$props.idHatoSelec)
    },
    mounted() { },
    computed: {},
    watch: {},
    methods: {},
};
</script>

<style></style>