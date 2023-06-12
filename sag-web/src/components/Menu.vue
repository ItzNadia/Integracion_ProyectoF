<template>
    <v-container>
        <v-card elevation="12">
            <v-navigation-drawer app permanent expand-on-hover>
                <v-list-item>
                    <v-list-item-content>
                        <v-list-item-title class="text-h6">
                            S A G
                        </v-list-item-title>
                    </v-list-item-content>
                </v-list-item>

                <v-divider />

                <v-list nav dense>
                    <v-list-item link href="#/">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-barn</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Principal</v-list-item-title>
                    </v-list-item>
                    <v-list-item link href="#/hatos">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-cow</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Hatos</v-list-item-title>
                    </v-list-item>
                    <v-list-item link href="#/traspasos">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-swap-horizontal-bold</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Traspasos</v-list-item-title>
                    </v-list-item>
                    <v-list-item link href="#/lotes">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-ballot-outline</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Lotes</v-list-item-title>
                    </v-list-item>
                    <v-list-item link @click="$toast.info('Esta sección aún no ha sido implementada...')">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-currency-usd</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Movimientos</v-list-item-title>
                    </v-list-item>
                    <v-list-item link @click="$toast.info('Esta sección aún no ha sido implementada...')">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-account-group</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Usuarios</v-list-item-title>
                    </v-list-item>
                    <v-list-item link @click="$toast.info('Esta sección aún no ha sido implementada...')">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-checkbox-marked-circle-outline</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Categorías</v-list-item-title>
                    </v-list-item>
                    <v-list-item link @click="$toast.success('Ya pasenos prefee, ajuuuuuaaaaaa~')">
                        <v-list-item-icon>
                            <v-icon color="black">mdi-account-cowboy-hat</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Ranchos</v-list-item-title>
                    </v-list-item>

                    <v-divider />

                    <v-list nav dense>
                        <v-list-item link>
                            <v-btn small @click="onClickCerrarSesion">
                                <v-icon color="black">mdi-account-arrow-right</v-icon>
                                Cerrar Sesión</v-btn>
                        </v-list-item>
                    </v-list>

                </v-list>
            </v-navigation-drawer>
        </v-card>
    </v-container>
</template>

<script>
export default {
    methods: {
        onClickCerrarSesion() {
            this.$session.destroy();
            this.$router.push({ name: "Login" })
        }
    },
};
</script>