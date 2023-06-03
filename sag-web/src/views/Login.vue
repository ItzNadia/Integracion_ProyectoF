<template>
    <v-container> 
        <!-- Panel de busqueda -->
        <v-row>
            <v-card elevation="15" outlined shaped align="center">
            <v-card-title>Bienvenido al sistema administrativo de ganado</v-card-title>
            <v-img
                contain
                max-height="450"
                max-width="550"
                :src="require('../assets/logo__sag.png')"
            ></v-img>
            <v-card-text class="text-md-center">
                <v-form ref="formLogin" v-model="valid" lazy-validation>
                    <v-row>
                        <v-col cols="12" md="4" sm="6">
                            <v-text-field 
                            v-model="sesion.usuario" 
                            label="Usuario"
                            :rules= "required"
                            > </v-text-field>
                        </v-col>
                        <v-col cols="12" md="4" sm="6">
                            <v-text-field 
                            v-model="sesion.contrasena" 
                            label="Contraseña"
                            type="password"
                            :rules= "required"
                            > </v-text-field>
                        </v-col>
                    </v-row>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn @click="onClickIniciarSesion" rounded color="green" dark>Iniciar sesión</v-btn>
            </v-card-actions>
            </v-card>
        </v-row>
    </v-container>
</template>

<script>
import {post} from "../api/Requests";

export default{
    name: "Login",
    propos: {},
    data(){
        return{
            valid: false,
            loader: false,
            sesion:{
                usuario: null,
                contrasena: null,
            },
            required: [(v) => !!v || "Este campo es requerido"],
        };
    },
    created(){},
    mounted(){

    },
    computed: {},
    watch: {},
    methods:  {
        onClickIniciarSesion(){
            if(this.$refs.formLogin.validate()){
                this.iniciarSesion();
            }
        },
        async iniciarSesion(){
            const response = await post("/sesion/login/", this.sesion);
            if(response.error === true){
                console.log(response)
                return;
            }else{
                console.log(response);
                this.$session.start();
                this.$session.set("user", JSON.parse(response.respuesta));
                this.$router.push({name: "Principal"});
            }
        },
        
    },
};
</script>

<style> </style>
