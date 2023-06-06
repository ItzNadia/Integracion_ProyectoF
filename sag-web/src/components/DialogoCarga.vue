<template>
    <v-dialog v-model="loader" persistent max-width="200" transition="dialog-transition">
        <v-card>
            <v-card-title />
            <v-card-text>
                <div class="text-center">
                    <v-progress-circular :size="90" color="primary" indeterminate />
                </div>
                <br/>
                {{ mensaje }}
            </v-card-text>
            <v-card-actions />
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    name: "DialogoCarga",
    props: {
        loader: {
            type: Boolean,
            required: false,
        },
        mensaje: {
            type: String,
            required: false,
            default: "Cargando datos...",
        }
    },
};
</script>