<template>
    <v-container>
        <!-- Panel de busqueda -->
        <v-row cols="12">
            <v-col cols="12" sm="7" class="mx-auto">
                <br>
                <br>
                <v-card elevation="15" outlined shaped>
                    <div margin:auto>
                        <br>
                        <h1>Bienvenido al sistema administrativo de ganado</h1>
                    </div>
                    <v-img class="mx-auto" contain max-height="450" max-width="550"
                        :src="require('../assets/logo__sag.png')"></v-img>
                    <v-card-text class="text-md-center">
                        <v-form ref="formLogin" v-model="valid" lazy-validation>
                            <v-row>
                                <v-col cols="8" class="mx-auto">
                                    <v-text-field v-model="sesion.usuario" label="Usuario" :rules="required">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="8" class="mx-auto">
                                    <v-text-field v-model="sesion.contrasena" label="Contraseña" type="password"
                                        :rules="required"> </v-text-field>
                                </v-col>
                            </v-row>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn class="mx-auto" @click="onClickIniciarSesion" rounded color="green" dark>Iniciar
                            sesión</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>

        <dialogoCarga :loader="loader"></dialogoCarga>
    </v-container>
</template>

<script>
import { post } from "../api/Requests"
import dialogoCarga from '../components/DialogoCarga.vue'
export default {
    name: "Login",
    components: {
        dialogoCarga,
    },
    props: {},
    data() {
        return {
            valid: false,
            loader: false,
            sesion: {
                usuario: null,
                contrasena: null,
            },
            required: [(v) => !!v || "Este campo es requerido"],
        };
    },
    created() {
        if (this.$session.has("user")) {
            this.$router.push({ name: "Principal" })
        }
    },
    mounted() {
    },
    computed: {},
    watch: {},
    methods: {
        onClickIniciarSesion() {
            if (this.$refs.formLogin.validate()) {
                this.iniciarSesion()
            }
        },
        async iniciarSesion() {
            this.loader = true
            const response = await post("/sesion/login/", this.sesion)
            this.loader = false
            if (response.error === true) {
                this.$toast.error(response.mensaje)
                return;
            }
            else {
                if((JSON.parse(response.respuesta)).idEstatus != 101){
                    this.$toast.warning("Este usuario no se encuentra activo...")
                } else {
                    console.log(response)
                    this.$session.start()
                    this.$session.set("user", JSON.parse(response.respuesta))
                    this.$router.push({ name: "Principal" })
                }
            }
        },
    },
};
</script>

<style></style>
