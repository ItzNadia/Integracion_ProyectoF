<template>
    <v-container fluid>
        <!--Panel de búsqueda-->
        <v-row>
            <v-col>
                <v-card elevation="2" width="100%" outlined shaped dense class="ml-20 mr-20">
                    <v-card-title>Buscar Hato</v-card-title>
                    <v-card-text>
                        <v-form ref="formBusqueda" v-model="valid">
                            <v-row>
                                <v-col cols="12" md="4" sm="6">
                                    <v-text-field v-model="filtro.busqueda" label="NDH o DIIO" maxlength="106" counter required />
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
                <v-data-table :headers="encabezadosHatos" :items="datosHatos" :items-per-page="5" class="ml-5 mr-5" dense>
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
                                    <v-btn icon v-bind="attrs" @click="onClickBajaHato(item)">
                                        <v-icon color="red" v-on="on">mdi-cow-off</v-icon>
                                    </v-btn>
                                </template>
                                <span>Dar de baja</span>
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
                                <v-text-field v-model="hato.diio" label="DIIO" :rules="required" maxlength="106" counter required/>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catRazas" label="Raza" item-value="idCatalogo" item-text="nombre"
                                    @change="changeRaza" :rules="required" v-model="idRazaSelec"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catLotes" label="Lote" item-value="idLote" item-text="nombre"
                                    @change="changeLote" :rules="required" v-model="idLoteSelec"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catSexo" label="Sexo" item-value="idSexo" item-text="nombre"
                                    @change="changeSexo" :rules="required" v-model="idSexoSelec"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-select :items="catEstatus" label="Estatus" item-value="idCatalogo" item-text="nombre"
                                    @change="changeEstatus" :rules="required" v-model="idEstatusSelec"></v-select>
                            </v-col>
                            <v-col cols="12" md="6" sm="4">
                                <v-text-field v-model="hato.descripcion" label="Descripción" :rules="required" maxlength="250" counter required/>
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
                            <v-date-picker v-model="hato.fechaBaja" :max="this.hato.fechaBaja" elevation="5"/>
                        </v-row>
                        <v-row>
                        <br>
                            <v-textarea v-model="hato.motivoBaja" label="Motivo de la baja" maxlength="500" counter required auto-grow filled/>
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

        <dialogoCarga :loader="loader"></dialogoCarga>
    </v-container>
</template>

<script>
import { get, post } from "../api/Requests"
import dialogoCarga from '../components/DialogoCarga.vue'
export default {
    name: "Hatos",
    components: {
        dialogoCarga,
    },
    props: {},
    data() {
        return {
            valid: false,
            loader: false,
            hatoIsNew: false,
            filtro: {
                busqueda: "",
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
                    value: 'fechaEdicion',
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
            dialogoBajaHato: false,
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
            catSexo: [
                { idSexo: 1, nombre: 'Hembra (H)' },
                { idSexo: 2, nombre: 'Macho (M)' },
            ],
            idSexoSelec: null,
            catRazas: [],
            idRazaSelec: null,
            catLotes: [],
            idLoteSelec: null,
            catEstatus: [],
            idEstatusSelec: null,
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
        async cargarHatos() {
            this.loader = true
            this.datosHatos = []
            const response = await get("/hato/getHatosByIdRancho/" + this.$session.get("user").idRancho)
            this.loader = false

            if (response.length === 0) {
                this.$toast.info("No se encuentran hatos registrados")
            } else {
                this.datosHatos = response
            }
        },
        async buscarRequest() {
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
        async cargarPropiedadesHatos() {
            this.catRazas = null
            this.catLotes = null
            this.catEstatus = null

            var response = await get("/catalogo/getCatalogosByCategoria/3")

            if (response.length === 0) {
                this.$toast.warning("No se encuentran razas disponibles...")
            } else {
                this.catRazas = response
            }

            response = await get("/lote/getLotesByIdRancho/" + this.$session.get("user").idRancho)

            if (response.length === 0) {
                this.$toast.warning("No se encuentran lotes disponibles...")
            } else {
                this.catLotes = response
            }

            response = await get("/catalogo/getCatalogosByCategoria/1")

            if (response.length === 0) {
                this.$toast.warning("No se encuentran estatus disponibles...")
            } else {
                this.catEstatus = response
            }
        },
        async formHatoRequest() {
            this.loader = true
            var respuestaForm
            this.hato.idRancho = this.$session.get("user").idRancho

            if (this.hatoIsNew) {
                this.hato.idUsuarioAlta = this.$session.get("user").idUsuario
                respuestaForm = await post("/hato/registrarHato", this.hato)
            } else {
                this.hato.idUsuarioEditor = this.$session.get("user").idUsuario
                respuestaForm = await post("/hato/editarHato", this.hato)
            }

            if (respuestaForm.error) {
                this.$toast.error(respuestaForm.mensaje)
            } else {
                this.$toast.success(respuestaForm.mensaje)
                this.limpiarHato()
                this.$refs.formHato.reset()
                this.dialogoHato = false
            }

            this.cargarHatos()
        },
        limpiarHato() {
            this.hato.idHato = null
            this.hato.diio = null
            this.hato.idRaza = null
            this.hato.raza = null
            this.hato.idLote = null
            this.hato.lote = null
            this.hato.sexo = null
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
        async onClickNuevoHato() {
            this.loader = true
            this.hatoIsNew = true
            await this.cargarPropiedadesHatos()
            this.dialogoHato = true
            this.loader = false
        },
        async onClickEditarHato(item) {
            if (item.idEstatus === 102){
                this.$toast.error("Este hato se encuentra cancelado, imposible modificar")
            }else{
                this.loader = true
                this.hatoIsNew = false
                await this.cargarPropiedadesHatos()
                this.hato = { ...item }
                this.idRazaSelec = this.hato.idRaza
                this.idLoteSelec = this.hato.idLote
                if(this.hato.sexo === "H"){
                    this.idSexoSelec = 1
                } else {
                    this.idSexoSelec = 2
                }
                this.idEstatusSelec = this.hato.idEstatus
                this.loader = false
                this.dialogoHato = true
            }
        },
        async onClickBajaHato(item){
            console.log(item)
            if (item.idEstatus === 102){
                this.$toast.error("Este hato ya se encuentra cancelado...")
            }else{
                this.loader = true

                this.hato.fechaBaja = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
                console.log("fachaPicker: " + this.hato.fechaBaja)

                this.loader = false
                this.dialogoBajaHato = true
            }
        },
        onClickEliminarHato(item) {
            console.log(item)
        },
        onClickBuscar() {
            this.buscarRequest()
        },
        onClickLimpiar() {
            this.$refs.formBusqueda.reset()
            this.filtro.busqueda = ""
            this.cargarHatos()
            /*            this.$toast.success("success")
                        this.$toast.error("error")
                        this.$toast.warning("warning")
                        this.$toast.info("info")
                        this.$toast.default("default")*/
        },
        onClickGuardarHato() {
            if (this.$refs.formHato.validate()) {
                this.formHatoRequest()
            }
        },
        onClickCerrarHato() {
            this.$refs.formHato.reset()
            this.dialogoHato = false
        },
        onClickGuardarBajaHato(){
            this.dialogoBajaHato = false
        },
        onClickCerrarBajaHato(){
            this.dialogoBajaHato = false
        },
        changeRaza(value) {
            console.log(value)
            this.hato.idRaza = value
        },
        changeLote(value) {
            console.log(value)
            this.hato.idLote = value
        },
        changeEstatus(value) {
            console.log(value)
            this.hato.idEstatus = value
        },
        changeSexo(value) {
            console.log(value)
            if (value === 1) {
                this.hato.sexo = "H"
            } else {
                this.hato.sexo = "M"
            }
        },
    },
};
</script>

<style></style>