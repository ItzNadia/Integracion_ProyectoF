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
import modelo.pojos.Hato;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("hato")
public class HatoWS {

    @Context
    private UriInfo context;

    public HatoWS() {
    }

    @GET
    @Path("getHatosByIdRancho/{idRancho}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hato> getHatosByIdRancho(@PathParam("idRancho") Integer idRancho){
    List<Hato> list = new LinkedList<>();
    SqlSession conn = null;
    try{    
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Hato.getHatosByIdRancho", idRancho);
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
    @Path("buscarHatos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hato> buscarHatos(
            @FormParam("idRancho") String idRancho,
            @FormParam("busqueda") String busqueda){
        List<Hato> list = new LinkedList<>();
        SqlSession conn = null;
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("busqueda", busqueda);
            
            conn = MyBatisUtil.getSession();
            list = conn.selectList("Hato.buscarHatos", param);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return list;
    }
    
    @POST
    @Path("registrarHato")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarHato(
            @FormParam("diio") String diio,
            @FormParam("idRaza") Integer idRaza,
            @FormParam("idLote") Integer idLote,
            @FormParam("sexo") String sexo,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("descripcion") String descripcion,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("diio", diio);
            param.put("idRaza", idRaza);
            param.put("idLote", idLote);
            param.put("sexo", sexo);
            param.put("idEstatus", idEstatus);
            param.put("descripcion", descripcion);
            param.put("idRancho", idRancho);
            param.put("idUsuarioAlta", idUsuarioAlta);
            
            conn.insert("Hato.registrarHato", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Hato registrado correctamente!");
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
    @Path("editarHato")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarHato(
            @FormParam("idHato") Integer idHato,
            @FormParam("diio") String diio,
            @FormParam("idRaza") Integer idRaza,
            @FormParam("idLote") Integer idLote,
            @FormParam("sexo") String sexo,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("descripcion") String descripcion,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idHato", idHato);
            param.put("diio", diio);
            param.put("idRaza", idRaza);
            param.put("idLote", idLote);
            param.put("sexo", sexo);
            param.put("idEstatus", idEstatus);
            param.put("descripcion", descripcion);
            param.put("idRancho", idRancho);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Hato.editarHato", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Hato editado correctamente!");
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
    @Path("bajaHato")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta bajaHato(
            @FormParam("idHato") Double idHato,
            @FormParam("motivoBaja") String motivoBaja,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idHato", idHato);
            param.put("motivoBaja", motivoBaja);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Hato.bajaHato", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Hato dado de baja correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error de conexión, favor de intentar más tarde.");
        }finally{
            conn.close();
        }
        return res;
    }
}
