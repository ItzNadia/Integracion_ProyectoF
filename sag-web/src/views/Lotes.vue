<template>
    <v-container fluid>
        <v-row>
            <v-col cols="1">
                <Menu />
            </v-col>
            <v-col cols="11">
                <!--Panel de búsqueda-->
                <v-row>
                    <v-col>
                        <v-card elevation="2" width="100%" outlined shaped dense class="ml-20 mr-20">
                            <v-card-title>Buscar Lote</v-card-title>
                            <v-card-text>
                                <v-form ref="formBusqueda" v-model="valid">
                                    <v-row>
                                        <v-col cols="12" md="4" sm="6">
                                            <v-text-field v-model="filtro.busqueda" label="NDL o Nombre" maxlength="100"
                                                counter required />
                                        </v-col>
                                    </v-row>
                                </v-form>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer />
                                <v-btn @click="onClickBuscarLote" rounded color="primary" dark small>
                                    <v-icon dark left>mdi-magnify</v-icon>
                                    Buscar
                                </v-btn>
                                <v-btn @click="onClickLimpiarLote" rounded color="orange" dark small>
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
                        <v-btn rounded color="primary" dark small @click="onClickNuevoLote">
                            <v-icon dark left>mdi-plus-circle-outline</v-icon>
                            Nuevo Lote
                        </v-btn>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12">
                        <v-data-table :headers="encabezadosLote" :items="datosLotes" :items-per-page="10" class="ml-5 mr-5"
                            dense>
                            <template v-slot:item.acciones="{ item }">
                                <v-row>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn icon v-bind="attrs" @click="onClickEditarLote(item)">
                                                <v-icon color="primary" v-on="on">mdi-pencil</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Editar</span>
                                    </v-tooltip>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="item.idEstatus === 102"
                                                @click="onClickCambiarEstatusLote(item, true)" icon v-bind="attrs">
                                                <v-icon color="green lighten-2" v-on="on">mdi-arrow-up-bold-outline</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Activar</span>
                                    </v-tooltip>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="item.idEstatus === 101"
                                                @click="onClickCambiarEstatusLote(item, false)" icon v-bind="attrs">
                                                <v-icon color="red lighten-2" v-on="on">mdi-arrow-down-bold-outline</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Desactivar</span>
                                    </v-tooltip>
                                </v-row>
                            </template>
                            <template v-slot:item.estatus="{ item }">
                                <v-card-text v-if="item.idEstatus === 101" class="green--text">{{ item.estatus
                                }}</v-card-text>
                                <v-card-text v-if="item.idEstatus === 102" class="red--text">{{ item.estatus
                                }}</v-card-text>
                            </template>
                        </v-data-table>
                    </v-col>
                </v-row>

                <!--Sección detalle-->
                <v-dialog v-model="dialogoLote" persistent max-width="1000" transition="dialog-transition">
                    <v-card>
                        <v-card-title>Lote</v-card-title>
                        <v-card-text>
                            <v-form ref="formLote" v-model="valid">
                                <v-row justify="start" md="6">
                                    <v-col cols="12" md="6" sm="4">
                                        <v-text-field v-model="lote.nombre" label="Nombre*" :rules="required"
                                            maxlength="250" counter required />
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-text-field v-model="lote.descripcion" label="Descripción*" :rules="required"
                                            maxlength="250" counter required />
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-switch v-model="switchEstatusLote" :label="`Estatus*: ${switchEstatusLote}`"
                                            true-value="Activo" false-value="Inactivo" />
                                    </v-col>
                                </v-row><br>
                            </v-form>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer />
                            <v-btn @click="onClickGuardarDialogoLote" elevation="0" dark rounded width="120"
                                class="green px13 font-weight-regular pr-4" small>
                                <v-icon left>mdi-check</v-icon>
                                Guardar
                            </v-btn>
                            <v-btn @click="onClickCerrarDialogoLote" elevation="0" rounded text width="100"
                                class="red--text px13 font-weight-bold" small>
                                <v-icon left>mdi-close-circle</v-icon>
                                Cerrar
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-col>
        </v-row>

        <dialogoCarga :loader="loader" />
    </v-container>
</template>

<script>
import { get, post } from "../api/Requests"
import Menu from "@/components/Menu.vue";
import dialogoCarga from '../components/DialogoCarga.vue'
export default {
    name: "Lotes",
    components: {
        Menu,
        dialogoCarga,
    },
    props: [],
    data() {
        return {
            valid: false,

            loader: false,

            // Definición de Lote
            lote: {
                idLote: null,
                nombre: null,
                descripcion: null,
                idEstatus: null,
                estatus: null,
                idRancho: null,
                rancho: null,
                fechaAlta: null,
                idUsuarioAlta: null,
                usuarioAlta: null,
                fechaEdicion: null,
                idUsuarioEditor: null,
                usuarioEditor: null,
            },

            // Card de búsqueda lote
            filtro: {
                busqueda: "",
            },

            // Datos para la tabla
            encabezadosLote: [
                {
                    text: 'Acciones',
                    value: 'acciones',
                    sortable: false,
                    width: 70,
                }, {
                    text: 'NDL',
                    value: 'idLote',
                    align: 'center',
                    sortable: false,
                    width: 40,
                }, {
                    text: 'Nombre',
                    value: 'nombre',
                    align: 'center',
                    sortable: false,
                    width: 40,
                }, {
                    text: 'Descripción',
                    value: 'descripcion',
                    align: 'center',
                    sortable: false,
                    width: 40,
                },
                {
                    text: 'Estatus',
                    value: 'estatus',
                    align: 'center',
                    sortable: true,
                    width: 40,
                }, {
                    text: 'Rancho',
                    value: 'rancho',
                    align: 'center',
                    sortable: true,
                    width: 40,
                }, {
                    text: 'Fecha de alta',
                    value: 'fechaAlta',
                    align: 'start',
                    sortable: true,
                    width: 120,
                }, {
                    text: 'Usuario de alta',
                    value: 'usuarioAlta',
                    align: 'start',
                    sortable: true,
                    width: 200,
                }, {
                    text: 'Fecha de edición',
                    value: 'fechaEdicion',
                    align: 'start',
                    sortable: true,
                    width: 120,
                }, {
                    text: 'Usuario editor',
                    value: 'usuarioEditor',
                    align: 'start',
                    sortable: true,
                    width: 200,
                },
            ],
            datosLotes: [],

            // Dialogo de lote (nuevo y editar)
            dialogoLote: false,
            isNewLote: false,

            switchEstatusLote: "Inactivo",

            // Regla de campos vacíos
            required: [(v) => !!v || "Este campo es requerido"],
        };
    },
    created() {
        if (!this.$session.id() && !this.$session.has("user")) {
            this.$router.push({ name: "Login" })
        } else {
            this.cargarLotes();
        }
    },
    mounted() { },
    computed: {},
    watch: {},
    methods: {
        async onClickBuscarLote() {
            this.loader = true
            this.datosCrias = []
            const resBusqueda = await post("/lote/buscarLotes", { idRancho: this.$session.get("user").idRancho, busqueda: this.filtro.busqueda })
            this.loader = false

            if (resBusqueda.length < 1) {
                this.$toast.info("No se encontraron Lotes...")
            } else {
                this.datosLotes = resBusqueda
            }
        },
        onClickLimpiarLote() {
            this.$refs.formBusqueda.reset()
            this.filtro.busqueda = ""
            this.cargarLotes()
        },

        // Dialogo lote
        async onClickNuevoLote() {
            this.loader = true
            this.isNewLote = true
            this.switchEstatusLote = "Inactivo"
            this.loader = false
            this.dialogoLote = true
        },
        async onClickEditarLote(item) {
            this.loader = true
            this.isNewLote = false

            // carga los datos para los comboBox (v-select) y cargalos datos del lote seleccionado
            this.lote = { ...item }
            this.switchEstatusLote = this.lote.estatus

            this.loader = false
            this.dialogoLote = true
        },
        async onClickCambiarEstatusLote(item, band) {
            this.loader = true

            var respuestaForm

            if (band) {
                respuestaForm = await post("/lote/editarEstatusLote", { idLote: item.idLote, idEstatus: 101, idUsuarioEditor: this.$session.get("user").idUsuario })
            } else {
                respuestaForm = await post("/lote/editarEstatusLote", { idLote: item.idLote, idEstatus: 102, idUsuarioEditor: this.$session.get("user").idUsuario })
            }

            if (respuestaForm.error) {
                this.$toast.error(respuestaForm.mensaje)
            } else {
                this.$toast.success(respuestaForm.mensaje)
                this.cargarLotes();
            }

            this.loader = false
        },
        async onClickGuardarDialogoLote() {
            console.log(this.lote)
            if (this.$refs.formLote.validate()) {
                this.loader = true

                var respuestaForm
                this.lote.idRancho = this.$session.get("user").idRancho

                if (this.switchEstatusLote === "Activo") {
                    this.lote.idEstatus = 101
                } else {
                    this.lote.idEstatus = 102
                }

                if (this.isNewLote) {
                    this.lote.idUsuarioAlta = this.$session.get("user").idUsuario
                    respuestaForm = await post("/lote/registrarLote", this.lote)
                } else {
                    this.lote.idUsuarioEditor = this.$session.get("user").idUsuario
                    respuestaForm = await post("/lote/editarLote", this.lote)
                }

                if (respuestaForm.error) {
                    this.$toast.error(respuestaForm.mensaje)
                    this.loader = false
                } else {
                    this.$toast.success(respuestaForm.mensaje)
                    this.limpiarLote()
                    this.$refs.formLote.reset()
                    this.dialogoLote = false
                    this.cargarLotes()
                }
            }
        },
        onClickCerrarDialogoLote() {
            this.$refs.formLote.reset()
            this.dialogoLote = false
        },


        async cargarLotes() { // carga crias en la tabla de crias
            this.loader = true
            this.datosLotes = []
            //this.limpiarHato()
            const response = await get("/lote/getLotesByIdRancho/" + this.$session.get("user").idRancho)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran lotes registrados...")
            } else {
                this.datosLotes = response
            }
        },

        limpiarLote() {
            this.lote.idLote = null
            this.lote.nombre = null
            this.lote.descripcion = null;
            this.lote.idEstatus = null
            this.lote.estatus = null
            this.lote.idRancho = null
            this.lote.rancho = null
            this.lote.fechaAlta = null
            this.lote.idUsuarioAlta = null
            this.lote.usuarioAlta = null
            this.lote.fechaEdicion = null
            this.lote.idUsuarioEditor = null
            this.lote.usuarioEditor = null
            this.switchEstatusLote = "Inactivo"
        },
    },
};
</script>

<style></style>