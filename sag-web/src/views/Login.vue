<template>
    <v-container> 
        <!-- Panel de busqueda -->
        <v-row>
            <v-card elevation="15" outlined shaped>
            <v-card-title>Login</v-card-title>
            <v-img
                contain
                lazy-src="https://picsum.photos/id/11/10/6"
                max-height="150"
                max-width="250"
                src="https://picsum.photos/id/11/500/300"
            ></v-img>
            <v-card-text>
                <v-form ref="formLogin">
                    <v-row>
                        <v-col cols="12" md="4" sm="6">
                            <v-text-field 
                            v-model="usuario" 
                            label="Usuario"
                            required
                            > </v-text-field>
                        </v-col>
                        <v-col cols="12" md="4" sm="6">
                            <v-text-field 
                            v-model="contrasena" 
                            label="Contraseña"
                            type="password"
                            required
                            > </v-text-field>
                        </v-col>
                    </v-row>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-btn rounded color="green" dark>Iniciar sesión</v-btn>
            </v-card-actions>
            </v-card>
        </v-row>
    </v-container>
</template>

<script>

export default{
    name: "Login",
    propos: {},
    data(){
        return{
            valid: false,
            filtro:{
                usuario: null,
                contrasena: null,
            },
            encabezados:[],
            datos:[],
        };
    },
    created(){},
    mounted(){

    },
    computed: {},
    watch: {},
    methods:  {},
};
</script>

<style> </style>
