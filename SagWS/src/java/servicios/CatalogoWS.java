package servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Catalogo;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("catalogo")
public class CatalogoWS {

    @Context
    private UriInfo context;

    public CatalogoWS() {
    }

    @POST
    @Path("registrarCatalogo")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarCatalogo(
            @FormParam("idCatalogo") Integer idCatalogo,
            @FormParam("idCategoria") Integer idCategoria,
            @FormParam("nombre") String nombre,
            @FormParam("activo") String activo) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCatalogo", idCatalogo);
            param.put("idCategoria", idCategoria);
            param.put("nombre", nombre);
            param.put("activo", activo);

            conn.insert("Catalogo.registrarCatalogo", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Catalogo registrado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("No se pudo crear el catalogo...");
        } finally {
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarCatalogo")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarCatalogo(
            @FormParam("idCatalogo") Integer idCatalogo,
            @FormParam("idCategoria") Integer idCategoria,
            @FormParam("nombre") String nombre,
            @FormParam("activo") String activo) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCatalogo", idCatalogo);
            param.put("idCategoria", idCategoria);
            param.put("nombre", nombre);
            param.put("activo", activo);

            conn.update("Catalogo.editarCatalogo", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Catalogo editado con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("No se pudo editar el catalogo...");
        } finally {
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarEstatusCatalogo")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarEstatusCatalogo(
            @FormParam("idCatalogo") Integer idCatalogo,
            @FormParam("activo") String activo) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCatalogo", idCatalogo);
            param.put("activo", activo);

            conn.update("Catalogo.editarEstatusCatalogo", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Estatus de catalogo editado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error al editar el catalogo...");
        } finally {
            conn.close();
        }
        return res;
    }

    // get catalogo por idCategoria
    @GET
    @Path("getCatalogosByCategoria/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catalogo> getCatalogosByCategoria(@PathParam("id") Integer id) {
        SqlSession conn = null;
        List<Catalogo> catalogos = new ArrayList<Catalogo>();

        try {
            conn = MyBatisUtil.getSession();
            catalogos = conn.selectList("Catalogo.getCatalogosByCategoria", id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return catalogos;
    }

    @GET
    @Path("getConceptosMovimientos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catalogo> getConceptosMovimientos() {
        SqlSession conn = null;
        List<Catalogo> conceptos = new ArrayList<Catalogo>();

        try {
            conn = MyBatisUtil.getSession();
            conceptos = conn.selectList("Catalogo.getConceptosMovimientos");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return conceptos;
    }

    @GET
    @Path("getRolesUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catalogo> getRolesUsuarios() {
        SqlSession conn = null;
        List<Catalogo> conceptos = new ArrayList<Catalogo>();

        try {
            conn = MyBatisUtil.getSession();
            conceptos = conn.selectList("Catalogo.getRolesUsuarios");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return conceptos;
    }
}
