package servicios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Lote;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("lote")
public class LoteWS {

    @Context
    private UriInfo context;

    public LoteWS() {
    }

    @GET
    @Path("getLotesByIdRancho/{idRancho}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lote> getLotesByIdRancho(@PathParam("idRancho") Integer idRancho){
    List<Lote> list = new LinkedList<>();
    SqlSession conn = null;
    try{
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Lote.getLotesByIdRancho", idRancho);
    }catch(Exception ex){
        ex.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
        }
    }
        return list;
    }

    @POST
    @Path("buscarLotes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lote> buscarLotes(
            @FormParam("idRancho") Integer idRancho,
            @FormParam("busqueda") String busqueda){
        List<Lote> list = new LinkedList<>();
        SqlSession conn = null;
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("busqueda", busqueda);
            
            conn = MyBatisUtil.getSession();
            list = conn.selectList("Lote.buscarLotes", param);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return list;
    }
    
    @POST
    @Path("registrarLote")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarLote(
            @FormParam("nombre") String nombre,
            @FormParam("descripcion") String descripcion,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("nombre", nombre);
            param.put("descripcion", descripcion);
            param.put("idEstatus", idEstatus);
            param.put("idRancho", idRancho);
            param.put("idUsuarioAlta", idUsuarioAlta);
            
            conn.insert("Lote.registrarLote", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Lote registrado correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarLote")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarLote(
            @FormParam("idLote") Integer idLote,
            @FormParam("nombre") String nombre,
            @FormParam("descripcion") String descripcion,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idLote", idLote);
            param.put("nombre", nombre);
            param.put("descripcion", descripcion);
            param.put("idEstatus", idEstatus);
            param.put("idRancho", idRancho);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Lote.editarLote", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Lote editado correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarEstatusLote")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarEstatusCatalogo(
            @FormParam("idLote") Integer idLote,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idLote", idLote);
            param.put("idEstatus", idEstatus);
            param.put("idUsuarioEditor", idUsuarioEditor);

            conn.update("Lote.editarEstatusLote", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Estatus de Lote editado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        } finally {
            conn.close();
        }
        return res;
    }
}
