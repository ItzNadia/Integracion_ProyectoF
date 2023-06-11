<template>
    <v-container fluid>
        <!--Panel de búsqueda-->
        <v-row>
            <v-col>
                <v-card elevation="2" width="100%" outlined shaped dense class="ml-20 mr-20">
                    <v-card-title>Buscar Consultas Médicas</v-card-title>
                    <v-card-text>
                        <v-form ref="formBusqueda" v-model="valid">
                            <v-row>
                                <v-col cols="12" md="4" sm="6">
                                    <v-text-field v-model="filtro.busqueda" label="NDCM" maxlength="9" type="number" counter
                                        required />
                                </v-col>
                            </v-row>
                        </v-form>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn @click="onClickBuscarConsultasMedicas" rounded color="primary" dark small>
                            <v-icon dark left>mdi-magnify</v-icon>
                            Buscar
                        </v-btn>
                        <v-btn @click="onClickLimpiarConsultasMedicas" rounded color="orange" dark small>
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
                <v-btn rounded color="primary" dark small @click="onClickNuevaConsultasMedicas">
                    <v-icon dark left>mdi-plus-circle-outline</v-icon>
                    Nueva Consulta Médica
                </v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="12">
                <v-data-table :headers="encabezadosConsultaMedica" :items="datosConsultasMedicas" :items-per-page="5"
                    class="ml-5 mr-5" dense>
                    <template v-slot:item.acciones="{ item }">
                        <v-row>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn v-if="!item.cancelado" icon v-bind="attrs"
                                        @click="onClickEditarConsultaMedica(item)">
                                        <v-icon color="primary" v-on="on">mdi-pencil</v-icon>
                                    </v-btn>
                                </template>
                                <span>Editar</span>
                            </v-tooltip>
                            <v-tooltip bottom>
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn v-if="!item.cancelado" @click="onClickCancelarConsultaMedica(item, false)" icon
                                        v-bind="attrs">
                                        <v-icon color="red lighten-2" v-on="on">mdi-needle-off</v-icon>
                                    </v-btn>
                                </template>
                                <span>Cancelar</span>
                            </v-tooltip>
                        </v-row>
                    </template>
                    <template v-slot:item.estatus="{ item }">
                        <v-card-text v-if="item.idEstatus === 101" class="green--text">{{ item.estatus }}</v-card-text>
                        <v-card-text v-if="item.idEstatus === 102" class="red--text">{{ item.estatus }}</v-card-text>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>

        <!--Sección detalle-->
        <v-dialog v-model="dialogoConsultaMedica" persistent max-width="1000" transition="dialog-transition">
            <v-card>
                <v-card-title>Consulta Médica</v-card-title>
                <v-card-text>
                    <!--                    <v-form ref="formConsultaMedica" v-model="valid">
                        <v-row justify="start">
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catSexo" label="Sexo*" item-value="idSexo" item-text="nombre"
                                    @change="changeSexo" :rules="required" v-model="idSexoSelec" /><br>
                                <v-select :items="catRazas" label="Raza*" item-value="idCatalogo" item-text="nombre"
                                    @change="changeRaza" :rules="required" v-model="idRazaSelec" /><br>
                                <v-text-field v-model="cria.observaciones" label="Observaciones*" :rules="required"
                                    maxlength="250" counter required /><br>
                                <v-switch v-model="switchEstatusCria" :label="`Estatus*: ${switchEstatusCria}`"
                                    true-value="Activo" false-value="Inactivo" />
                            </v-col>
                            <v-col>
                                <h3>Fecha de nacimiento*</h3>
                                <v-date-picker v-model="cria.fechaNacimiento"
                                    :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
                                    elevation="5" />
                            </v-col>
                        </v-row><br>
                    </v-form>-->
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn @click="onClickGuardarDialogoConsultaMedica" elevation="0" dark rounded width="120"
                        class="green px13 font-weight-regular pr-4" small>
                        <v-icon left>mdi-check</v-icon>
                        Guardar
                    </v-btn>
                    <v-btn @click="onClickCerrarDialogoConsultaMedica" elevation="0" rounded text width="100"
                        class="red--text px13 font-weight-bold" small>
                        <v-icon left>mdi-close-circle</v-icon>
                        Cerrar
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <dialogoCarga :loader="loader" />
    </v-container>
</template>

<script>
import { get, post } from "../api/Requests"
import dialogoCarga from '../components/DialogoCarga.vue'
export default {
    name: "ConsultasMedicas",
    components: {
        dialogoCarga,
    },
    props: ['idHatoSelec'],
    data() {
        return {
            valid: false,

            loader: false,

            // Definición de cria
            consultaMedica: {
                idConsultaMedica: null,
                idHato: null,
                nombreVeterinario: null,
                fechaAtencion: null,
                observaciones: null,
                idMotivoAtencion: null,
                motivoAtencion: null,
                cancelado: null,
                idRancho: null,
                rancho: null,
                fechaAlta: null,
                idUsuarioAlta: null,
                usuarioAlta: null,
                fechaEdicion: null,
                idUsuarioEditor: null,
                usuarioEditor: null,
            },

            // Card de búsqueda hato
            filtro: {
                busqueda: "",
            },

            // Datos para la tabla
            encabezadosCria: [
                {
                    text: 'NDCM',
                    value: 'idConsultaMedica',
                    sortable: false,
                    width: 70,
                }, {
                    text: 'NDH',
                    value: 'idHato',
                    align: 'center',
                    sortable: false,
                    width: 40,
                }, {
                    text: 'Nombre del veterinario',
                    value: 'nombreVeterinario',
                    align: 'center',
                    sortable: false,
                    width: 40,
                }, {
                    text: 'Fecha de atención',
                    value: 'fechaAtencion',
                    align: 'center',
                    sortable: true,
                    width: 40,
                }, {
                    text: 'observaciones',
                    value: 'observaciones',
                    align: 'start',
                    sortable: true,
                    width: 110,
                }, {
                    text: 'Raza',
                    value: 'raza',
                    align: 'start',
                    sortable: true,
                    width: 100,
                }, {
                    text: 'Estatus',
                    value: 'estatus',
                    align: 'center',
                    sortable: true,
                    width: 40,
                }, {
                    text: 'Observaciones',
                    value: 'observaciones',
                    align: 'start',
                    sortable: true,
                    width: 250,
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
            datosCrias: [],

            // Dialogo de cría (nuevo y editar)
            dialogoCria: false,
            isNewCria: false,

            catRazas: [],
            idRazaSelec: null,
            catLotes: [],
            idLoteSelec: null,
            catSexo: [
                { idSexo: 1, nombre: 'Hembra (H)' },
                { idSexo: 2, nombre: 'Macho (M)' },
            ],
            idSexoSelec: null,
            switchEstatusCria: "Inactivo",

            // Regla de campos vacíos
            required: [(v) => !!v || "Este campo es requerido"],
        };
    },
    created() {
        if (this.$props.idHatoSelec != null) {
            this.cargarCriasByIdHato()
        } else {
            this.cargarCrias()
        }
    },
    mounted() { },
    computed: {},
    watch: {},
    methods: {
        async onClickBuscarCria() {
            this.loader = true
            this.datosCrias = []
            const resBusqueda = await post("/cria/buscarCrias", { idRancho: this.$session.get("user").idRancho, idCria: this.filtro.busqueda })
            this.loader = false

            if (resBusqueda.length < 1) {
                this.$toast.info("No se encuentraron crías...")
            } else {
                this.datosCrias = resBusqueda
            }
        },
        onClickLimpiarCria() {
            this.$refs.formBusqueda.reset()
            this.filtro.busqueda = ""
            this.cargarCrias()
        },

        // Dialogo cria
        async onClickNuevaCria() {
            if (this.$props.idHatoSelec != null) {
                this.loader = true
                this.isNewCria = true
                await this.cargarPropiedadesCrias() // carga los datos para los comboBox (v-select)
                this.cria.fechaNacimiento = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
                this.cria.idHatoMadre = this.$props.idHatoSelec
                this.switchEstatusCria = "Inactivo"
                this.loader = false
                this.dialogoCria = true
            } else {
                this.$toast.warning("Para agregar una nueva cría debe seleccionar un progenitor primero.")
            }
        },
        async onClickEditarCria(item) {
            this.loader = true
            this.isNewCria = false

            // carga los datos para los comboBox (v-select) y cargalos datos de la cría seleccionada
            this.cria = { ...item }
            this.cria.fechaNacimiento = (this.cria.fechaNacimiento.substr(6, 10) + "-" + this.cria.fechaNacimiento.substr(3, 2) + "-" + this.cria.fechaNacimiento.substr(0, 2))
            await this.cargarPropiedadesCrias()
            this.idRazaSelec = this.cria.idRaza
            this.idLoteSelec = this.cria.idLote
            if (this.cria.sexo === "H") {
                this.idSexoSelec = 1
            } else {
                this.idSexoSelec = 2
            }
            this.switchEstatusCria = this.cria.estatus

            this.loader = false
            this.dialogoCria = true
        },
        async onClickCambiarEstatusCria(item, band) {
            this.loader = true

            var respuestaForm

            if (band) {
                respuestaForm = await post("/cria/editarEstatusCria", { idCria: item.idCria, idEstatus: 101, idUsuarioEditor: this.$session.get("user").idUsuario })
            } else {
                respuestaForm = await post("/cria/editarEstatusCria", { idCria: item.idCria, idEstatus: 102, idUsuarioEditor: this.$session.get("user").idUsuario })
            }

            if (respuestaForm.error) {
                this.$toast.error(respuestaForm.mensaje)
            } else {
                if (this.$props.idHatoSelec === null) {
                    this.cargarCrias()
                } else {
                    this.cargarCriasByIdHato()
                }
                this.$toast.success(respuestaForm.mensaje)
            }

            this.loader = false
        },
        async onClickGuardarDialogoCria() {
            console.log(this.cria)
            if (this.$refs.formCria.validate()) {
                this.loader = true

                var respuestaForm
                this.cria.idRancho = this.$session.get("user").idRancho

                if (this.switchEstatusCria === "Activo") {
                    this.cria.idEstatus = 101
                } else {
                    this.cria.idEstatus = 102
                }

                if (this.isNewCria) {
                    this.cria.idUsuarioAlta = this.$session.get("user").idUsuario
                    respuestaForm = await post("/cria/registrarCria", this.cria)
                } else {
                    this.cria.idUsuarioEditor = this.$session.get("user").idUsuario
                    respuestaForm = await post("/cria/editarCria", this.cria)
                }

                if (respuestaForm.error) {
                    this.$toast.error(respuestaForm.mensaje)
                    this.loader = false
                } else {
                    this.$toast.success(respuestaForm.mensaje)
                    this.limpiarCria()
                    this.$refs.formCria.reset()
                    this.dialogoCria = false
                    this.cargarCriasByIdHato()
                }
            }
        },
        onClickCerrarDialogoCria() {
            this.$refs.formCria.reset()
            this.dialogoCria = false
        },
        async cargarPropiedadesCrias() { // carga los datos para los comboBox (v-select)
            this.catRazas = null
            this.catLotes = null

            var response = await get("/catalogo/getCatalogosByCategoria/3") // obtiene razas

            if (response.length === 0) {
                this.$toast.warning("No se encuentran razas disponibles...")
            } else {
                this.catRazas = response
            }

            response = await get("/lote/getLotesByIdRancho/" + this.$session.get("user").idRancho) // obtiene lotes

            if (response.length === 0) {
                this.$toast.warning("No se encuentran lotes disponibles...")
            } else {
                this.catLotes = response
            }
        },
        changeRaza(value) { // Cuando se selecciona algo de los comboBox (v-select)
            this.cria.idRaza = value
        },
        changeLote(value) {
            this.cria.idLote = value
        },
        changeSexo(value) {
            if (value === 1) {
                this.cria.sexo = "H"
            } else {
                this.cria.sexo = "M"
            }
        },


        async cargarCriasByIdHato() { // carga crias en la tabla de crias
            this.loader = true
            this.datosCrias = []
            //this.limpiarHato()
            const response = await get("/cria/getCriasByIdHatoMadre/" + this.$props.idHatoSelec)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran crías registradas...")
            } else {
                this.datosCrias = response
            }
        },

        async cargarCrias() { // carga crias en la tabla de crias
            this.loader = true
            this.datosCrias = []
            //this.limpiarHato()
            const response = await get("/cria/getCriasByIdRancho/" + this.$session.get("user").idRancho)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran crías registradas...")
            } else {
                this.datosCrias = response
            }
            this.$toast.info("Todas las crías del rancho han sido cargadas")
        },

        limpiarCria() {
            this.cria.idCria = null
            this.cria.idHatoMadre = null
            this.cria.sexo = null
            this.cria.fechaNacimiento = null
            this.cria.idRaza = null
            this.cria.raza = null
            this.cria.idEstatus = null
            this.cria.estatus = null
            this.cria.observaciones = null
            this.cria.idRancho = null
            this.cria.rancho = null
            this.cria.fechaAlta = null
            this.cria.idUsuarioAlta = null
            this.cria.usuarioAlta = null
            this.cria.fechaEdicion = null
            this.cria.idUsuarioEditor = null
            this.cria.usuarioEditor = null
            this.switchEstatusHato = "Inactivo"
        }
    },
};
</script>

<style></style>