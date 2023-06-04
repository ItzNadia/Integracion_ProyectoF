<template>
    <v-container fluid>
        <!--Panel de búsqueda-->
        <v-row cols="12">
            <v-col cols="12">
                <v-card elevation="2" width="100%" outlined shaped dense class="ml-20 mr-20">
                    <v-card-title>Buscar Hato</v-card-title>
                    <v-card-text>
                        <v-form ref="formBusqueda" v-model="valid">
                            <v-row>
                                <v-col cols="12" md="4" sm="6">
                                    <v-text-field v-model="filtro.busqueda" label="NDH o DIIO" min="0" max="106" required />
                                </v-col>
                            </v-row>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn @click="onClickBuscar" rounded color="primary" dark small>
                            <v-icon dark left>mdi-magnify</v-icon>
                            Buscar
                        </v-btn>
                        <v-btn @click="onClickLimpiar" rounded color="orange" dark small>
                            <v-icon dark left>mdi-backspace</v-icon>
                            Limpiar
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
        <!--Sección tabla principal-->
        <v-row align="start" justify="start">
            <v-col cols="2">
                <v-btn rounded color="indigo" dark small @click="onClickNuevoHato">
                    <v-icon dark left>mdi-plus-circle-outline</v-icon>
                    Nuevo Hato
                </v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12">
                <v-data-table :headers="encabezadosHatos" :items="datosHatos" :items-per-page="15" class="ml-5 mr-5" dense>
                    <template v-slot:item.acciones="{ item }">
                        <v-row>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn icon v-bind="attrs" @click="onClickEditarHato(item)">
                                        <v-icon color="primary" v-on="on">mdi-pencil</v-icon>
                                    </v-btn>
                                </template>
                                <span>Editar</span>
                            </v-tooltip>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn icon v-bind="attrs" @click="onClickBaja(this)">
                                        <v-icon color="red" v-on="on">mdi-cow-off</v-icon>
                                    </v-btn>
                                </template>
                                <span>Desactivar</span>
                            </v-tooltip>
                        </v-row>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
        <!--Sección detalle-->
        <v-row></v-row>

        <v-dialog v-model="dialogoHato" persistent max-width="1000" transition="dialog-transition">
            <v-card>
                <v-card-title>Hato</v-card-title>
                <v-card-text>
                    <v-form ref="formHato" v-model="valid">
                        <v-row justify="start">
                            <v-col cols="12" md="6" sm="4">
                                <v-text-field v-model="hato.diio" label="DIIO" />
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catRazas" label="Raza" item-value="idCatalogo" item-text="nombre"
                                    @change="changeRaza"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catLotes" label="Lote" item-value="idLote" item-text="nombre"
                                    @change="changeLote"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-text-field v-model="hato.sexo" label="Sexo" />
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catEstatus" label="Estatus" item-value="idCatalogo" item-text="nombre"
                                    @change="changeEstatus"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-text-field v-model="hato.descripcion" label="Descripción" />
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-date-picker v-model="hato.fechaBaja" />
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-text-field v-model="hato.motivoBaja" label="Motivo de la baja" />
                            </v-col>
                        </v-row>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn @click="onClickGuardarHato" elevation="0" dark rounded width="120"
                        class="green px13 font-weight-regular pr-4" small>
                        <v-icon left>mdi-check</v-icon>
                        Guardar
                    </v-btn>
                    <v-btn @click="onClickCerrarHato" elevation="0" rounded text width="100"
                        class="red--text px13 font-weight-bold" small>
                        <v-icon left>mdi-close-circle</v-icon>
                        Cerrar
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <dialogoCarga :loader="loader"></dialogoCarga>
    </v-container>
</template>

<script>
import { get, post } from "../api/Requests"
import dialogoCarga from '../components/DialogoCarga.vue'
export default {
    name: "Principal",
    components: {
        dialogoCarga,
    },
    props: {},
    data() {
        return {
            valid: false,
            loader: false,
            filtro: {
                busqueda: null,
            },
            encabezadosHatos: [
                {
                    text: 'Acciones',
                    value: 'acciones',
                    sortable: false,
                    windth: 100,
                }, {
                    text: 'NDH',
                    value: 'idHato',
                    align: 'start',
                    sortable: false,
                    windth: 100,
                }, {
                    text: 'DIIO',
                    value: 'diio',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Raza',
                    value: 'raza',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Lote',
                    value: 'lote',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Sexo',
                    value: 'sexo',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Estatus',
                    value: 'estatus',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Descripción',
                    value: 'descripcion',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Fecha de baja',
                    value: 'fechaBaja',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Motivo de la baja',
                    value: 'motivoBaja',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Rancho',
                    value: 'rancho',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Fecha de alta',
                    value: 'fechaAlta',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Usuario de alta',
                    value: 'usuarioAlta',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Fecha de edición',
                    value: 'fechaBaja',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                }, {
                    text: 'Usuario editor',
                    value: 'usuarioEditor',
                    align: 'start',
                    sortable: true,
                    windth: 255,
                },
            ],
            datosHatos: [],
            dialogoHato: false,
            hato: {
                idHato: null,
                diio: null,
                idRaza: null,
                raza: null,
                idLote: null,
                lote: null,
                sexo: null,
                idEstatus: null,
                estatus: null,
                descripcion: null,
                fechaBaja: null,
                motivoBaja: null,
                idRancho: null,
                rancho: null,
                fechaAlta: null,
                idUsuarioAlta: null,
                usuarioAlta: null,
                fechaEdicion: null,
                idUsuarioEditor: null,
                usuarioEditor: null,
            },
            catRazas: [],
            catLotes: [],
            catEstatus: [],
        };
    },
    created() {
        if (!this.$session.id() && !this.$session.has("user")) {
            this.$router.push({ name: "Login" })
        } else {
            this.cargarHatos();
        }
    },
    mounted() {
    },
    computed: {},
    watch: {},
    methods: {
        async cargarHatos() {
            this.loader = true
            const response = await get("/hato/getHatosByIdRancho/" + this.$session.get("user").idRancho)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran hatos registrados")
            }else{
                this.datosHatos = response
            }
        },
        async wasd() {
            this.loader = true
            const response = await get("/hato/getAllHatos/")
            this.loader = false
            if (response.error === true) {
                this.$toast.error(response.mensaje)
                return;
            }
            else {

            }
        },
        onClickNuevoHato() {
            this.dialogoHato = true;
        },
        onClickEditarHato(item) {
            this.hato = { ...item }
            this.dialogoHato = true
        },
        onClickEliminarHato(item) {
            console.log(item)
        },
        onClickBuscar() {

        },
        onClickLimpiar() {
            this.$refs.formBusqueda.reset()
            this.$toast.open("open")
            this.$toast.success("success")
            this.$toast.error("error")
            this.$toast.warning("warning")
            this.$toast.info("info")
            this.$toast.default("default")
        },
        onClickGuardarHato() {

        },
        onClickCerrarHato() {
            this.$refs.formHato.reset()
            this.dialogoHato = false
        },
        changeRaza(value) {
            console.log(value)
        },
        changeLote(value) {
            console.log(value)
        },
        changeEstatus(value) {
            console.log(value)
        },
        changeEstados() {

        },
    },
};
</script>

<style></style>