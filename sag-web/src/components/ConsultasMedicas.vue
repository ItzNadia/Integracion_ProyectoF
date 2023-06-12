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
                                    <v-text-field v-model="filtro.busqueda" label="NDCM" maxlength="50" counter required />
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
        <v-row v-if="$props.idHatoSelec != null" align="start" justify="start">
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
                                    <v-btn v-if="!item.cancelado" @click="onClickCancelarConsultaMedica(item)" icon
                                        v-bind="attrs">
                                        <v-icon color="red lighten-2" v-on="on">mdi-needle-off</v-icon>
                                    </v-btn>
                                </template>
                                <span>Cancelar</span>
                            </v-tooltip>
                        </v-row>
                    </template>
                    <template v-slot:item.cancelado="{ item }">
                        <v-card-text v-if="!item.cancelado" class="green--text">No</v-card-text>
                        <v-card-text v-if="item.cancelado" class="red--text">Sí</v-card-text>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>

        <!--Sección detalle-->
        <v-dialog v-model="dialogoConsultaMedica" persistent max-width="1000" transition="dialog-transition">
            <v-card>
                <v-card-title>Consulta Médica</v-card-title>
                <v-card-text>
                    <v-form ref="formConsultaMedica" v-model="valid">
                        <v-row justify="start">
                            <v-col cols="12" md="6" sm="4">
                                <v-text-field v-model="consultaMedica.nombreVeterinario" label="Nombre del veterinario*"
                                    :rules="required" maxlength="100" counter required /><br>
                                <v-text-field v-model="consultaMedica.observaciones" label="Observaciones*"
                                    :rules="required" maxlength="500" counter required /><br>
                                <v-select :items="catMotivosAtencion" label="Motivo de atención*" item-value="idCatalogo"
                                    item-text="nombre" @change="changeMotivosAtencion" :rules="required"
                                    v-model="idMotivoAtencionSelec" /><br>
                            </v-col>
                            <v-col>
                                <h3>Fecha de atención*</h3>
                                <v-date-picker v-model="consultaMedica.fechaAtencion"
                                    :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
                                    elevation="5" />
                            </v-col>
                        </v-row><br>
                    </v-form>
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
            encabezadosConsultaMedica: [
                {
                    text: 'Acciones',
                    value: 'acciones',
                    sortable: false,
                    width: 40,
                }, {
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
                    text: 'Motivo de atencion',
                    value: 'motivoAtencion',
                    align: 'start',
                    sortable: true,
                    width: 100,
                }, {
                    text: 'Cancelado',
                    value: 'cancelado',
                    align: 'center',
                    sortable: true,
                    width: 40,
                }, {
                    text: 'Rancho',
                    value: 'rancho',
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
            datosConsultasMedicas: [],

            // Dialogo de cría (nuevo y editar)
            dialogoConsultaMedica: false,
            isNewConsultaMedica: false,

            catMotivosAtencion: [],
            idMotivoAtencionSelec: null,
            switchEstatusConsultaMedica: "Inactivo",

            // Regla de campos vacíos
            required: [(v) => !!v || "Este campo es requerido"],
        };
    },
    created() {
        if (this.$props.idHatoSelec != null) {
            this.cargarConsultasMedicasByIdHato()
        } else {
            this.$toast.info("Seleccione un hato primero")
        }
    },
    mounted() { },
    computed: {},
    watch: {},
    methods: {
        async onClickBuscarConsultasMedicas() {
            this.loader = true
            this.datosConsultasMedicas = []
            const resBusqueda = await post("/consultaMedica/buscarConsultasMedicas", { idRancho: this.$session.get("user").idRancho, busqueda: this.filtro.busqueda })
            this.loader = false

            if (resBusqueda.length < 1) {
                this.$toast.info("No se encontraron consultas médicas...")
            } else {
                this.datosConsultasMedicas = resBusqueda
            }
        },
        onClickLimpiarConsultasMedicas() {
            this.$refs.formBusqueda.reset()
            this.filtro.busqueda = ""
            this.cargarConsultasMedicasByIdHato()
        },

        // Dialogo Consulta Medica
        async onClickNuevaConsultasMedicas() {
            if (this.$props.idHatoSelec != null) {
                this.loader = true
                this.isNewConsultaMedica = true
                await this.cargarPropiedadesConsultasMedicas() // carga los datos para los comboBox (v-select)
                this.consultaMedica.fechaAtencion = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
                this.consultaMedica.idHato = this.$props.idHatoSelec
                //this.switchEstatusCria = "Inactivo"
                this.loader = false
                this.dialogoConsultaMedica = true
            } else {
                this.$toast.warning("Para agregar una nueva consultas médica debe seleccionar un hato primero.")
            }
        },
        async onClickEditarConsultaMedica(item) {
            this.loader = true
            this.isNewConsultaMedica = false

            // carga los datos para los comboBox (v-select) y cargalos datos de la cría seleccionada
            this.consultaMedica = { ...item }
            this.consultaMedica.fechaAtencion = (this.consultaMedica.fechaAtencion.substr(6, 10) + "-" + this.consultaMedica.fechaAtencion.substr(3, 2) + "-" + this.consultaMedica.fechaAtencion.substr(0, 2))
            await this.cargarPropiedadesConsultasMedicas()
            this.idMotivoAtencionSelec = this.consultaMedica.idMotivoAtencion

            this.loader = false
            this.dialogoConsultaMedica = true
        },
        async onClickCancelarConsultaMedica(item) {
            if(confirm('¿Seguro que quiere cancelar esta consulta medica?')){
            this.loader = true

            const response = await post("/consultaMedica/cancelarConsultaMedica", { idConsultaMedica: item.idConsultaMedica, idUsuarioEditor: this.$session.get("user").idUsuario })

            if (response.error) {
                this.$toast.error(response.mensaje)
            } else {
                this.$toast.success(response.mensaje)
                this.cargarConsultasMedicas()
            }

            this.loader = false
        } 
        },
        async onClickGuardarDialogoConsultaMedica() {
            if (this.$refs.formConsultaMedica.validate()) {
                this.loader = true

                var respuestaForm
                this.consultaMedica.idRancho = this.$session.get("user").idRancho
                if (this.isNewConsultaMedica) {
                    this.consultaMedica.idUsuarioAlta = this.$session.get("user").idUsuario
                    respuestaForm = await post("/consultaMedica/registrarConsultaMedica", this.consultaMedica)
                } else {
                    this.consultaMedica.idUsuarioEditor = this.$session.get("user").idUsuario
                    respuestaForm = await post("/consultaMedica/editarConsultaMedica", this.consultaMedica)
                }

                if (respuestaForm.error) {
                    this.$toast.error(respuestaForm.mensaje)
                    this.loader = false
                } else {
                    this.$toast.success(respuestaForm.mensaje)
                    this.limpiarConsultaMedica()
                    this.$refs.formConsultaMedica.reset()
                    this.dialogoConsultaMedica = false
                    this.cargarConsultasMedicasByIdHato()
                }
            }
        },
        onClickCerrarDialogoConsultaMedica() {
            this.$refs.formConsultaMedica.reset()
            this.dialogoConsultaMedica = false
        },
        async cargarPropiedadesConsultasMedicas() { // carga los datos para los comboBox (v-select)
            this.catMotivosAtencion = null

            var response = await get("/catalogo/getCatalogosByCategoria/5")

            if (response.length === 0) {
                this.$toast.warning("No se encuentran motivos disponibles...")
            } else {
                this.catMotivosAtencion = response
            }
        },
        changeMotivosAtencion(value) { // Cuando se selecciona algo de los comboBox (v-select)
            this.consultaMedica.idMotivoAtencion = value
        },


        async cargarConsultasMedicasByIdHato() { // carga crias en la tabla de crias
            this.loader = true
            this.datosConsultasMedicas = []
            //this.limpiarHato()
            const response = await get("/consultaMedica/getConsultasMedicasByIdHato/" + this.$props.idHatoSelec)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran consultas médicas registradas...")
            } else {
                this.datosConsultasMedicas = response
            }
        },

        limpiarConsultaMedica() {
            this.consultaMedica.idConsultaMedica = null
            this.consultaMedica.idHato = null
            this.consultaMedica.nombreVeterinario = null
            this.consultaMedica.fechaAtencion = null
            this.consultaMedica.observaciones = null
            this.consultaMedica.idMotivoAtencion = null
            this.consultaMedica.motivoAtencion = null
            this.consultaMedica.cancelado = null
            this.consultaMedica.idRancho = null
            this.consultaMedica.rancho = null
            this.consultaMedica.fechaAlta = null
            this.consultaMedica.idUsuarioAlta = null
            this.consultaMedica.usuarioAlta = null
            this.consultaMedica.fechaEdicion = null
            this.consultaMedica.idUsuarioEditor = null
            this.consultaMedica.usuarioEditor = null
        }
    },
};
</script>

<style></style>