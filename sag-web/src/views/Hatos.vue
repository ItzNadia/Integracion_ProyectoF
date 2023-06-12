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
                            <v-card-title>Buscar Hato</v-card-title>
                            <v-card-text>
                                <v-form ref="formBusqueda" v-model="valid">
                                    <v-row>
                                        <v-col cols="12" md="4" sm="6">
                                            <v-text-field v-model="filtro.busqueda" label="NDH o DIIO" maxlength="106"
                                                counter required />
                                        </v-col>
                                    </v-row>
                                </v-form>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer />
                                <v-btn @click="onClickBuscarHato" rounded color="primary" dark small>
                                    <v-icon dark left>mdi-magnify</v-icon>
                                    Buscar
                                </v-btn>
                                <v-btn @click="onClickLimpiarHato" rounded color="orange" dark small>
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
                        <v-btn rounded color="primary" dark small @click="onClickNuevoHato">
                            <v-icon dark left>mdi-plus-circle-outline</v-icon>
                            Nuevo Hato
                        </v-btn>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12">
                        <v-data-table :headers="encabezadosHatos" :items="datosHatos" @click:row="onClickTablaHato"
                            item-key="idHato" single-select :items-per-page="10" class="ml-5 mr-5" dense>
                            <template v-slot:item.acciones="{ item }">
                                <v-row>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="item.idEstatus != 103" icon v-bind="attrs"
                                                @click="onClickEditarHato(item)">
                                                <v-icon color="primary" v-on="on">mdi-pencil</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Editar</span>
                                    </v-tooltip>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="item.idEstatus === 102"
                                                @click="onClickCambiarEstatusHato(item, true)" icon v-bind="attrs">
                                                <v-icon color="green lighten-2" v-on="on">mdi-arrow-up-bold-outline</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Establecer como activo</span>
                                    </v-tooltip>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="item.idEstatus === 101"
                                                @click="onClickCambiarEstatusHato(item, false)" icon v-bind="attrs">
                                                <v-icon color="red lighten-2" v-on="on">mdi-arrow-down-bold-outline</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Establecer como inactivo</span>
                                    </v-tooltip>
                                    <v-tooltip bottom>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-btn v-if="item.idEstatus != 103" icon v-bind="attrs"
                                                @click="onClickBajaHato(item)">
                                                <v-icon color="gray" v-on="on">mdi-cow-off</v-icon>
                                            </v-btn>
                                        </template>
                                        <span>Dar de baja</span>
                                    </v-tooltip>
                                </v-row>
                            </template>
                            <template v-slot:item.estatus="{ item }">
                                <v-card-text v-if="item.idEstatus === 101" class="green--text">{{ item.estatus
                                }}</v-card-text>
                                <v-card-text v-if="item.idEstatus === 102" class="red--text">{{ item.estatus
                                }}</v-card-text>
                                <v-card-text v-if="item.idEstatus === 103" class="orange--text">{{ item.estatus
                                }}</v-card-text>
                            </template>
                        </v-data-table>
                    </v-col>
                </v-row>

                <v-card elevation="10">
                    <v-tabs ref="wasd" centered icons-and-text>
                        <v-tabs-slider></v-tabs-slider>

                        <v-tab @click="onClickTabCria">
                            Crías
                            <v-icon>mdi-baby-bottle-outline</v-icon>
                        </v-tab>

                        <v-tab @click="onClickTabConsultaMedica">
                            Consultas Médicas
                            <v-icon>mdi-medical-bag</v-icon>
                        </v-tab>
                    </v-tabs>

                    <Crias v-if="componentCria" :idHatoSelec="idHatoSelec" />
                    <ConsultasMedicas v-if="componentConsultaMedica" :idHatoSelec="idHatoSelec" />
                </v-card>

                <!--Sección detalle-->
                <v-dialog v-model="dialogoHato" persistent max-width="1000" transition="dialog-transition">
                    <v-card>
                        <v-card-title>Hato</v-card-title>
                        <v-card-text>
                            <v-form ref="formHato" v-model="valid">
                                <v-row justify="start">
                                    <v-col cols="12" md="6" sm="4">
                                        <v-text-field v-model="hato.diio" label="DIIO*" :rules="required" maxlength="106"
                                            counter required />
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-switch v-model="switchEstatusHato" :label="`Estatus*: ${switchEstatusHato}`"
                                            true-value="Activo" false-value="Inactivo"></v-switch>
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-select :items="catRazas" label="Raza*" item-value="idCatalogo" item-text="nombre"
                                            @change="changeRaza" :rules="required" v-model="idRazaSelec"></v-select>
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-select :items="catSexo" label="Sexo*" item-value="idSexo" item-text="nombre"
                                            @change="changeSexo" :rules="required" v-model="idSexoSelec"></v-select>
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-text-field v-model="hato.descripcion" label="Descripción*" :rules="required"
                                            maxlength="250" counter required />
                                    </v-col>
                                    <v-col cols="12" md="6" sm="4">
                                        <v-select :items="catLotes" label="Lote*" item-value="idLote" item-text="nombre"
                                            @change="changeLote" :rules="required" v-model="idLoteSelec"
                                            v-if="isNewHato"></v-select>
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

                <v-dialog v-model="dialogoBajaHato" persistent max-width="1000" transition="dialog-transition">
                    <v-card>
                        <v-card-title>Baja hato</v-card-title>
                        <v-card-text>
                            <v-form ref="formBajaHato" v-model="valid">
                                <p>Fecha Baja*</p>
                                <v-row justify="center">
                                    <v-date-picker v-model="hato.fechaBaja"
                                        :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
                                        elevation="5" />
                                </v-row>
                                <br /><br />
                                <v-row>
                                    <v-textarea v-model="hato.motivoBaja" label="Motivo de la baja*" maxlength="500" counter
                                        required auto-grow filled :rules="required" />
                                </v-row>
                            </v-form>
                        </v-card-text>
                        <v-card-actions>
                            <v-spacer />
                            <v-btn @click="onClickGuardarBajaHato" elevation="0" dark rounded width="120"
                                class="green px13 font-weight-regular pr-4" small>
                                <v-icon left>mdi-check</v-icon>
                                Guardar
                            </v-btn>
                            <v-btn @click="onClickCerrarBajaHato" elevation="0" rounded text width="100"
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
import dialogoCarga from '../components/DialogoCarga.vue'
import Menu from "@/components/Menu.vue";
import ConsultasMedicas from "@/components/ConsultasMedicas.vue";
import Crias from "@/components/Crias.vue";
export default {
    name: "Hatos",
    components: {
        dialogoCarga,
        Menu,
        ConsultasMedicas,
        Crias,
    },
    props: {},
    data() {
        return {
            valid: false,

            // Loader
            loader: false,

            // Definición de hato
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

            // Card de búsqueda hato
            filtro: {
                busqueda: "",
            },

            // Datos para la tabla
            encabezadosHatos: [
                {
                    text: 'Acciones',
                    value: 'acciones',
                    sortable: false,
                    width: 120,
                    align: "center"
                }, {
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
                    text: 'Lote',
                    value: 'lote',
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
                }, {
                    text: 'Estatus',
                    value: 'estatus',
                    align: "start",
                    sortable: true,
                    width: 100,
                }, {
                    text: 'Fecha de baja',
                    value: 'fechaBaja',
                    align: "start",
                    sortable: true,
                    width: 120,
                }, {
                    text: 'Motivo de la baja',
                    value: 'motivoBaja',
                    align: "start",
                    sortable: true,
                    width: 250,
                }, {
                    text: 'Rancho',
                    value: 'rancho',
                    align: "start",
                    sortable: true,
                    width: 200,
                }, {
                    text: 'Fecha de alta',
                    value: 'fechaAlta',
                    align: "start",
                    sortable: true,
                    width: 120,
                }, {
                    text: 'Usuario de alta',
                    value: 'usuarioAlta',
                    align: "start",
                    sortable: true,
                    width: 200,
                }, {
                    text: 'Fecha de edición',
                    value: 'fechaEdicion',
                    align: "start",
                    sortable: true,
                    width: 120,
                }, {
                    text: 'Usuario editor',
                    value: 'usuarioEditor',
                    align: "start",
                    sortable: true,
                    width: 200,
                },
            ],
            datosHatos: [],
            rowSelected: null,

            // Dialogo de hato (nuevo y editar)
            dialogoHato: false,
            isNewHato: false,

            catRazas: [],
            idRazaSelec: null,
            catLotes: [],
            idLoteSelec: null,
            catSexo: [
                { idSexo: 1, nombre: 'Hembra (H)' },
                { idSexo: 2, nombre: 'Macho (M)' },
            ],
            idSexoSelec: null,
            switchEstatusHato: "Inactivo",

            // Dialogo de hato (baja)
            dialogoBajaHato: false,

            // tabs
            idHatoSelec: null,
            componentCria: false,
            componentConsultaMedica: false,

            // Regla de campos vacíos
            required: [(v) => !!v || "Este campo es requerido"],
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
        // Card de búsqueda hato
        async onClickBuscarHato() {
            this.loader = true
            this.datosHatos = []
            const resBusqueda = await post("/hato/buscarHatos", { idRancho: this.$session.get("user").idRancho, busqueda: this.filtro.busqueda })
            this.loader = false

            if (resBusqueda.length === 0) {
                this.$toast.info("No se encuentran hatos registrados")
            } else {
                this.datosHatos = resBusqueda
            }
        },
        onClickLimpiarHato() {
            this.$refs.formBusqueda.reset()
            this.filtro.busqueda = ""
            this.idHatoSelec = null
            if (this.rowSelected != null) {
                this.rowSelected.select(false)
            }
            this.componentCria = false
            this.componentConsultaMedica = false
            this.cargarHatos()
        },


        // Dialogo de hato
        async onClickNuevoHato() {
            this.loader = true
            this.isNewHato = true
            await this.cargarPropiedadesHatos() // carga los datos para los comboBox (v-select)
            this.switchEstatusHato = "Inactivo"
            this.loader = false
            this.dialogoHato = true
        },
        async onClickEditarHato(item) {
            if (item.idEstatus === 103) {
                this.$toast.error("Este hato se encuentra cancelado, imposible modificar")
            } else {
                this.loader = true
                this.isNewHato = false

                // carga los datos para los comboBox (v-select) y carga los datos del hato seleccionado dentro del dialogo hato
                this.hato = { ...item }
                await this.cargarPropiedadesHatos()
                this.idRazaSelec = this.hato.idRaza
                this.idLoteSelec = this.hato.idLote
                if (this.hato.sexo === "H") {
                    this.idSexoSelec = 1
                } else {
                    this.idSexoSelec = 2
                }
                this.switchEstatusHato = this.hato.estatus

                this.loader = false
                this.dialogoHato = true
            }
        },
        async onClickCambiarEstatusHato(item, band) {
            if(confirm('¿Seguro que quiere cambiar el estatus de este hato?')){
            
            this.loader = true

            var respuestaForm

            if (band) {
                respuestaForm = await post("/hato/editarEstatusHato", { idHato: item.idHato, idEstatus: 101, idUsuarioEditor: this.$session.get("user").idUsuario })
            } else {
                respuestaForm = await post("/hato/editarEstatusHato", { idHato: item.idHato, idEstatus: 102, idUsuarioEditor: this.$session.get("user").idUsuario })
            }

            if (respuestaForm.error) {
                this.$toast.error(respuestaForm.mensaje)
            } else {
                this.cargarHatos()
                this.$toast.success(respuestaForm.mensaje)
            }

            this.loader = false
        }
        },
        async cargarPropiedadesHatos() { // carga los datos para los comboBox (v-select)
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
        async onClickGuardarHato() {
            if (this.$refs.formHato.validate()) {
                this.loader = true

                var respuestaForm
                this.hato.idRancho = this.$session.get("user").idRancho

                if (this.switchEstatusHato === "Activo") {
                    this.hato.idEstatus = 101
                } else {
                    this.hato.idEstatus = 102
                }

                if (this.isNewHato) {
                    this.hato.idUsuarioAlta = this.$session.get("user").idUsuario
                    respuestaForm = await post("/hato/registrarHato", this.hato)
                } else {
                    this.hato.idUsuarioEditor = this.$session.get("user").idUsuario
                    respuestaForm = await post("/hato/editarHato", this.hato)
                }

                if (respuestaForm.error) {
                    this.$toast.error(respuestaForm.mensaje)
                    this.loader = false
                } else {
                    this.$toast.success(respuestaForm.mensaje)
                    this.limpiarHato()
                    this.$refs.formHato.reset()
                    this.dialogoHato = false
                    this.cargarHatos()
                }
            }
        },
        onClickCerrarHato() {
            this.$refs.formHato.reset()
            this.dialogoHato = false
        },
        changeRaza(value) { // Cuando se selecciona algo de los comboBox (v-select)
            this.hato.idRaza = value
        },
        changeLote(value) {
            this.hato.idLote = value
        },
        changeSexo(value) {
            if (value === 1) {
                this.hato.sexo = "H"
            } else {
                this.hato.sexo = "M"
            }
        },


        // Dialogo baja hato
        async onClickBajaHato(item) {
            if(confirm('¿Seguro que quiere dar de baja este hato?')){
            if (item.idEstatus === 103) {
                this.$toast.error("Este hato ya se encuentra cancelado...")
            } else {
                this.loader = true

                this.hato.idHato = item.idHato
                this.hato.fechaBaja = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)

                this.loader = false
                this.dialogoBajaHato = true
            }
        }
        },
        async onClickGuardarBajaHato() {
            if (this.$refs.formBajaHato.validate()) {
                this.loader = true

                const response = await post("/hato/bajaHato", { idHato: this.hato.idHato, fechaBaja: this.hato.fechaBaja, motivoBaja: this.hato.motivoBaja, idUsuarioEditor: this.$session.get("user").idUsuario })

                if (response.error) {
                    this.$toast.error(response.mensaje)
                } else {
                    this.$toast.success(response.mensaje)
                    this.cargarHatos()
                    this.dialogoBajaHato = false
                }
            }
        },
        onClickCerrarBajaHato() {
            this.dialogoBajaHato = false
            this.limpiarHato()
        },

        async cargarHatos() { // carga hatos en la tabla de hatos
            this.loader = true
            this.datosHatos = []
            this.limpiarHato()
            const response = await get("/hato/getHatosByIdRancho/" + this.$session.get("user").idRancho)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran hatos registrados")
            } else {
                this.datosHatos = response
            }
        },

        limpiarHato() { // Limpia el objeto de hato para estar vacíos
            this.hato.idHato = null
            this.hato.diio = null
            this.hato.idRaza = null
            this.hato.raza = null
            this.hato.idLote = null
            this.hato.lote = null
            this.hato.sexo = null
            this.switchEstatusHato = "Inactivo"
            this.hato.idEstatus = null
            this.hato.estatus = null
            this.hato.descripcion = null
            this.hato.fechaBaja = null
            this.hato.motivoBaja = null
            this.hato.idRancho = null
            this.hato.rancho = null
            this.hato.fechaAlta = null
            this.hato.idUsuarioAlta = null
            this.hato.usuarioAlta = null
            this.hato.fechaEdicion = null
            this.hato.idUsuarioEditor = null
            this.hato.usuarioEditor = null
        },

        async onClickTablaHato(item, row) {
            //console.log(item.idHato)
            //console.log(row)
            this.rowSelected = row
            this.rowSelected.select(true)
            this.idHatoSelec = item.idHato
            this.componentCria = false
            this.componentConsultaMedica = false
        },

        onClickTabCria() {
            if (this.idHatoSelec != null) {
                this.componentConsultaMedica = false
                this.componentCria = true
            } else {
                this.$toast.info("Seleccione un hato primero")
            }
        },
        onClickTabConsultaMedica() {
            if (this.idHatoSelec != null) {
                this.componentCria = false
                this.componentConsultaMedica = true
            } else {
                this.$toast.info("Seleccione un hato primero")
            }
        },
    },
};
</script>

<style></style>