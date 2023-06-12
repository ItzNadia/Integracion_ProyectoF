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
                            <v-card-title>Buscar traspaso</v-card-title>
                            <v-card-text>
                                <v-form ref="formBusqueda" v-model="valid">
                                    <v-row>
                                        <v-col cols="12" md="4" sm="6">
                                            <v-text-field v-model="filtro.busqueda" label="NDT o Lote (anterior o destino)"
                                                maxlength="50" counter required />
                                        </v-col>
                                    </v-row>
                                </v-form>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer />
                                <v-btn @click="onClickBuscarTraspaso" rounded color="primary" dark small>
                                    <v-icon dark left>mdi-magnify</v-icon>
                                    Buscar
                                </v-btn>
                                <v-btn @click="onClickLimpiarBuscarTraspaso" rounded color="orange" dark small>
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
                        <v-btn rounded color="primary" dark small @click="onClickNuevoTraspaso">
                            <v-icon dark left>mdi-plus-circle-outline</v-icon>
                            Nuevo Traspaso
                        </v-btn>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12">
                        <v-data-table :headers="encabezadosTraspaso" :items="datosTraspasos" :items-per-page="10"
                            class="ml-5 mr-5" dense>
                            <template v-slot:item.acciones="{ item }">
                                <v-row>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn @click="onClickVerhatosTraspaso(item)" icon
                                                v-bind="attrs">
                                                <v-icon color="black" v-on="on">mdi-eye</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Ver hatos del traspaso</span>
                                    </v-tooltip>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="!item.cancelado" @click="onClickCancelarTraspaso(item)" icon
                                                v-bind="attrs">
                                                <v-icon color="red lighten-2" v-on="on">mdi-arrow-down-bold-outline</v-icon>
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
            </v-col>
        </v-row>

        <v-dialog v-model="dialogoTraspaso" persistent max-width="1000" transition="dialog-transition">
            <v-card>
                <v-card-title>Traspaso</v-card-title>
                <v-card-text>
                    <v-form ref="formTraspaso" v-model="valid">
                        <v-row>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catLoteAnterior" label="Lote anterior*" item-value="idLote"
                                    item-text="nombre" @change="changeLoteAnterior" :rules="required"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catLoteDestino" label="Lote destino*" item-value="idLote"
                                    item-text="nombre" @change="changeLoteDestino" :rules="required"></v-select>
                            </v-col>
                        </v-row>
                        <br>
                    </v-form>
                    <v-data-table v-model="hatosSeleccionados" item-key="idHato" :headers="encabezadosHatos"
                        :items="datosHatos" :items-per-page="10" class="ml-5 mr-5 elevation-5" dense single-expand
                        show-select />
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn @click="onClickGuardarTraspaso" elevation="0" dark rounded width="120"
                        class="green px13 font-weight-regular pr-4" small>
                        <v-icon left>mdi-check</v-icon>
                        Guardar
                    </v-btn>
                    <v-btn @click="onClickCerrarTraspaso" elevation="0" rounded text width="100"
                        class="red--text px13 font-weight-bold" small>
                        <v-icon left>mdi-close-circle</v-icon>
                        Cerrar
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-dialog v-model="dialogoVerHatosDelTraspaso" persistent max-width="1000" transition="dialog-transition">
            <v-card>
                <v-card-title>{{ this.vhLoteAnt }} ==> {{ this.vhLoteDes }}</v-card-title>
                <v-card-text>
                    <v-data-table :headers="encabezadosHatos" :items="hatosTraspaso" :items-per-page="10"
                        class="ml-5 mr-5 elevation-5" dense />
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn @click="onClickCerrarHatosDelTraspaso" elevation="0" rounded text width="100"
                        class="red--text px13 font-weight-bold" small>
                        <v-icon left>mdi-close-circle</v-icon>
                        Cerrar
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-dialog v-model="dialogoCancelar" persistent max-width="1000" transition="dialog-transition">
            <v-card>
                <v-card-title>Cancelar traspaso</v-card-title>
                <v-card-text>
                    <v-form ref="formCancelacion" v-model="valid">
                        <v-textarea v-model="traspaso.motivoCancelacion" label="Motivo de cancelación*" maxlength="300"
                            counter required auto-grow filled :rules="required" />
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn @click="onClickGuardarCancelacion" elevation="0" dark rounded width="120"
                        class="green px13 font-weight-regular pr-4" small>
                        <v-icon left>mdi-check</v-icon>
                        Guardar
                    </v-btn>
                    <v-btn @click="onClickCerrarCancelacion" elevation="0" rounded text width="100"
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
import Menu from "@/components/Menu.vue";
import dialogoCarga from '../components/DialogoCarga.vue'
export default {
    name: "Traspasos",
    components: {
        Menu,
        dialogoCarga,
    },
    props: {},
    data() {
        return {
            valid: false,
            loader: false,

            // Definición de Traspaso
            traspaso: {
                idTraspaso: null,
                idLoteAnterior: null,
                loteAnterior: null,
                idLoteDestino: null,
                loteDestino: null,
                cancelado: null,
                fechaCancelacion: null,
                motivoCancelacion: null,
                idRancho: null,
                rancho: null,
                fechaAlta: null,
                idUsuarioAlta: null,
                usuarioAlta: null,
                fechaEdicion: null,
                idUsuarioEditor: null,
                usuarioEditor: null,
            },

            // Card de búsqueda Traspaso
            filtro: {
                busqueda: "",
            },

            // Dialogo de traspaso
            dialogoTraspaso: false,

            catLoteAnterior: [],
            catLoteDestino: [],

            encabezadosHatos: [
                {
                    text: 'NDH',
                    value: 'idHato',
                    sortable: false,
                    width: 40,
                    align: "center"
                }, {
                    text: 'DIIO',
                    value: 'diio',
                    align: "start",
                    sortable: true,
                    width: 100,
                }, {
                    text: 'Raza',
                    value: 'raza',
                    align: "start",
                    sortable: true,
                    width: 100,
                }, {
                    text: 'Sexo',
                    value: 'sexo',
                    align: "center",
                    sortable: true,
                    width: 40,
                }, {
                    text: 'Descripción',
                    value: 'descripcion',
                    align: "start",
                    sortable: true,
                    width: 250,
                },
            ],
            datosHatos: [],

            hatosSeleccionados: [],


            // Datos para la tabla
            encabezadosTraspaso: [
                {
                    text: 'Acciones',
                    value: 'acciones',
                    sortable: false,
                    width: 45,
                }, {
                    text: 'NDT',
                    value: 'idTraspaso',
                    align: 'center',
                    sortable: true,
                    width: 40,
                }, {
                    text: 'Lote anterior',
                    value: 'loteAnterior',
                    align: 'start',
                    sortable: true,
                    width: 60,
                }, {
                    text: 'Lote destino',
                    value: 'loteDestino',
                    align: 'start',
                    sortable: true,
                    width: 60,
                }, {
                    text: 'Cancelado',
                    value: 'cancelado',
                    align: 'start',
                    sortable: true,
                    width: 60,
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
                    text: 'Fecha de cancelación',
                    value: 'fechaCancelacion',
                    align: 'start',
                    sortable: true,
                    width: 60,
                }, {
                    text: 'Motivo de cancelación',
                    value: 'motivoCancelacion',
                    align: 'start',
                    sortable: true,
                    width: 60,
                }, {
                    text: 'Usuario editor',
                    value: 'usuarioEditor',
                    align: 'start',
                    sortable: true,
                    width: 200,
                },
            ],
            datosTraspasos: [],

            // Dialogo cancelar
            dialogoCancelar: false,

            dialogoVerHatosDelTraspaso: false,
            vhLoteAnt: "",
            vhLoteDes: "",
            hatosTraspaso: [],

            // Regla de campos vacíos
            required: [(v) => !!v || "Este campo es requerido"],
        };
    },
    created() {
        if (!this.$session.id() && !this.$session.has("user")) {
            this.$router.push({ name: "Login" })
        } else {
            this.cargarTraspasos()
        }
    },
    mounted() { },
    computed: {},
    watch: {},
    methods: {
        // Busqueda traspaso
        async onClickBuscarTraspaso() {
            this.loader = true
            this.datosTraspasos = []
            const resBusqueda = await post("/traspaso/buscarTraspasos", { idRancho: this.$session.get("user").idRancho, busqueda: this.filtro.busqueda })
            this.loader = false

            if (resBusqueda.length < 1) {
                this.$toast.info("No se encontraron traspasos...")
            } else {
                this.datosTraspasos = resBusqueda
            }
        },
        onClickLimpiarBuscarTraspaso() {
            this.$refs.formBusqueda.reset()
            this.filtro.busqueda = ""
            this.cargarTraspasos()
        },

        // tabla
        async onClickNuevoTraspaso() {
            this.loader = true

            this.catLoteAnterior = null
            this.catLoteDestino = null

            var response = await get("/lote/getLotesByIdRancho/" + this.$session.get("user").idRancho) // obtiene lotes

            if (response.length === 0) {
                this.$toast.warning("No se encuentran lotes disponibles...")
            } else {
                this.catLoteAnterior = response
                this.catLoteDestino = response
            }

            this.loader = false
            this.dialogoTraspaso = true
        },

        // Card traspasos
        async onClickGuardarTraspaso() {
            if (this.$refs.formTraspaso.validate()) {
                if (this.traspaso.idLoteAnterior != this.traspaso.idLoteDestino) {
                    if (this.hatosSeleccionados.length > 0) {
                        this.loader = true

                        var cantidadTraspasos = await get("/traspaso/getIdSiguienteTraspaso")
                        cantidadTraspasos = cantidadTraspasos.idTraspaso

                        this.traspaso.idRancho = this.$session.get("user").idRancho
                        this.traspaso.idUsuarioAlta = this.$session.get("user").idUsuario

                        var resNuevoTraspaso = await post("/traspaso/registrarTraspaso/", this.traspaso)

                        if (resNuevoTraspaso.error) {
                            this.$toast.error(resNuevoTraspaso.mensaje)
                        } else {
                            this.hatosSeleccionados.forEach(async element => {
                                await post("/traspaso/agregarHatosTraspaso", { idHato: element.idHato, idTraspaso: cantidadTraspasos, idLoteDestino: this.traspaso.idLoteDestino })
                            });
                            this.$toast.success(resNuevoTraspaso.mensaje)
                            this.dialogoTraspaso = false
                            this.cargarTraspasos()
                        }

                        this.hatosSeleccionados = []

                        this.loader = false
                    } else {
                        this.$toast.warning("Seleccione al menos un hato")
                    }
                } else {
                    this.$toast.warning("El lote anterior y el de destino no deben ser iguales")
                }

            }
        },
        onClickCerrarTraspaso() {
            this.limpiarTraspaso()
            this.$refs.formTraspaso.reset()
            this.datosHatos = []
            this.dialogoTraspaso = false
        },
        async changeLoteAnterior(value) { // Cuando se selecciona algo de los comboBox (v-select)
            this.traspaso.idLoteAnterior = value

            this.hatosSeleccionados = []
            this.datosHatos = []
            this.loader = true

            var response = await post("/hato/getHatosByIdLote/", { idRancho: this.$session.get("user").idRancho, idLote: value })

            if (response.length === 0) {
                this.$toast.info("No existen hatos asignados a este lote...")
            } else {
                this.datosHatos = response
            }

            this.loader = false
        },
        changeLoteDestino(value) {
            this.traspaso.idLoteDestino = value
        },

        // Acciones de la tabla
        async onClickVerhatosTraspaso(item) { // Cuando se selecciona algo de los comboBox (v-select)
            this.hatosTraspaso = []
            this.loader = true

            this.vhLoteAnt = item.loteAnterior
            this.vhLoteDes = item.loteDestino

            var response = await post("/hato/getHatosByIdTraspaso", { idRancho: this.$session.get("user").idRancho, idTraspaso: item.idTraspaso })

            if (response.length === 0) {
                this.$toast.info("No existen hatos asignados a este traspaso...")
            } else {
                this.hatosTraspaso = response
            }

            this.loader = false
            this.dialogoVerHatosDelTraspaso = true
        },
        async onClickCancelarTraspaso(item) {
            if(confirm('¿Seguro que quiere cancelar  esta Traspaso?')){
            this.traspaso.idTraspaso = item.idTraspaso
            this.dialogoCancelar = true
            }
        },
        onClickCerrarHatosDelTraspaso() {
            this.datosHatos = []
            this.dialogoVerHatosDelTraspaso = false
        },

        async onClickGuardarCancelacion() {
            if (this.$refs.formCancelacion.validate()) {
                this.loader = true

                const response = await post("/traspaso/cancelarTraspaso/", { idTraspaso: this.traspaso.idTraspaso, motivoCancelacion: this.traspaso.motivoCancelacion, idUsuarioEditor: this.$session.get("user").idUsuario })

                if (response.error) {
                    this.$toast.error(response.mensaje)
                } else {
                    this.$toast.success(response.mensaje)
                    this.cargarTraspasos()
                    this.dialogoCancelar = false
                }

                this.loader = true
            }
        },
        onClickCerrarCancelacion() {
            this.limpiarTraspaso()
            this.dialogoCancelar = false
        },

        async cargarTraspasos() { // carga crias en la tabla de crias
            this.loader = true
            this.datosTraspasos = []
            //this.limpiarHato()
            const response = await get("/traspaso/getTraspasosByIdRancho/" + this.$session.get("user").idRancho)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran lotes registrados...")
            } else {
                this.datosTraspasos = response
            }
        },
        limpiarTraspaso() {
            this.traspaso.idTraspaso = null
            this.traspaso.idLoteAnterior = null
            this.traspaso.loteAnterior = null
            this.traspaso.idLoteDestino = null
            this.traspaso.loteDestino = null
            this.traspaso.cancelado = null
            this.traspaso.fechaCancelacion = null
            this.traspaso.motivoCancelacion = null
            this.traspaso.idRancho = null
            this.traspaso.rancho = null
            this.traspaso.fechaAlta = null
            this.traspaso.idUsuarioAlta = null
            this.traspaso.usuarioAlta = null
            this.traspaso.fechaEdicion = null
            this.traspaso.idUsuarioEditor = null
            this.traspaso.usuarioEditor = null
        },
    },
};
</script>

<style></style>