<template>
  <div id="app">
    <v-app>
      <v-row>
        <v-col cols="1">
          <v-card elevation="12" >
            <v-navigation-drawer app permanent expand-on-hover>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="text-h6">
                    S A G
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <v-divider></v-divider>

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
                <v-list-item link>
                  <v-list-item-icon>
                    <v-icon color="black">mdi-swap-horizontal-bold</v-icon>
                  </v-list-item-icon>
                  <v-list-item-title>Traspasos</v-list-item-title>
                </v-list-item>
                <v-list-item link>
                  <v-list-item-icon>
                    <v-icon color="black">mdi-ballot-outline</v-icon>
                  </v-list-item-icon>
                  <v-list-item-title>Lotes</v-list-item-title>
                </v-list-item>

                <v-divider></v-divider>

                <v-list nav dense>
                <v-list-item link>
                  <v-btn small>
                    <v-icon color="black">mdi-account-arrow-right</v-icon>
                    Cerrar Sesión</v-btn>
                </v-list-item>
                </v-list>
                
              </v-list>
            </v-navigation-drawer>
          </v-card>
        </v-col>
        <v-col cols="11"><router-view /></v-col>
      </v-row>
    </v-app>
  </div>
</template>



<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
