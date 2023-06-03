const urlBase = "http://localhost:8084/SagWS/ws";
import axios from "axios";

function get(urlget) {
  var url = urlBase + urlget;
  return axios({
    method: "GET",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    url: url,
    validateStatus: (status) => {
      return true; // I'm always returning true, you may want to do it depending on the status received
    },
  })
    .then((response) => {
      /***Cuando la peticion es correcta */
      if (response.status >= 200 && response.status <= 299) {
        return response.data;
      } else {
        response.data = {
          error: true,
          mensaje: response.statusText,
        };
        return response.data;
      }
    })
    .catch((error) => {
      let response = { data: null };
      let msjError = "";
      if (error.response) {
        //console.log(error.response.status);
        msjError = "Error en la respuesta";
      } else if (error.request) {
        console.log("request", error.request);
        msjError = "Error en la petición";
      } else {
        //console.log("Error", error.message);
        msjError = "No se puede ejecutar la operación";
      }
      response.data = { error: true, mensaje: msjError };
      return response.data;
    });
}

function post(urlpost, params) {
  var url = urlBase + urlpost;
  return axios({
    method: "POST",
    withCredentials: false,
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      //"Content-Type": "application/octet-stream",
    },
    data: params,
    url: url,
    validateStatus: (status) => {
      return true;
    },
  })
    .then((response) => {
      if (response.status >= 200 && response.status <= 299) {
        return response.data;
      } else {
        response.data = {
          error: true,
          mensaje: response.statusText,
        };
        return response.data;
      }
    })
    .catch((error) => {
      let response = { data: null };
      let msjError = "";
      if (error.response) {
        msjError = "Error en la respuesta";
      } else if (error.request) {
        msjError =
          "Error en la petición, el servidor no se encuentra disponible por el momento. Por favor intente más tarde";
      } else {
        msjError = "No se puede ejecutar la operación";
      }
      response.data = { error: true, mensaje: msjError };
      return response.data;
    });
}
function put(urlput, params) {
  var url = urlBase + urlput;
  return axios({
    method: "PUT",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    data: params,
    url: url,
  })
    .then((response) => {
      if (response.status >= 200 && response.status <= 299) {
        return response.data;
      } else {
        response.data = {
          error: true,
          mensaje: response.statusText,
        };
        return response.data;
      }
    })
    .catch((error) => {
      let response = { data: null };
      let msjError = "";
      if (error.response) {
        msjError = "Error en la respuesta";
      } else if (error.request) {
        msjError =
          "Error en la petición, el servidor no se encuentra disponible por el momento. Por favor intente más tarde";
      } else {
        msjError = "No se puede ejecutar la operación";
      }
      response.data = { error: true, mensaje: msjError };
      return response.data;
    });
}
function del(urlput, params) {
  var url = urlBase + urlput;
  return axios({
    method: "DELETE",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    data: params,
    url: url,
  })
    .then((response) => {
      if (response.status >= 200 && response.status <= 299) {
        return response.data;
      } else {
        response.data = {
          error: true,
          mensaje: response.statusText,
        };
        return response.data;
      }
    })
    .catch((error) => {
      let response = { data: null };
      let msjError = "";
      if (error.response) {
        msjError = "Error en la respuesta";
      } else if (error.request) {
        msjError =
          "Error en la petición, el servidor no se encuentra disponible por el momento. Por favor intente más tarde";
      } else {
        msjError = "No se puede ejecutar la operación";
      }
      response.data = { error: true, mensaje: msjError };
      return response.data;
    });
}
export { post, get, put, del };
