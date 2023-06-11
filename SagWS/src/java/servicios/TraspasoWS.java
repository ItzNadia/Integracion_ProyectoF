package servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Respuesta;
import modelo.pojos.Traspaso;
import org.apache.ibatis.session.SqlSession;
import utils.JavaUtils;


@Path("traspaso")
public class TraspasoWS {

    @Context
    private UriInfo context;

    public TraspasoWS() {
    }

    @GET
    @Path("getTraspasosByIdRancho/{idRancho}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Traspaso> getTraspasosByIdRancho(@PathParam("idRancho") Integer idRancho){
    List<Traspaso> list = new ArrayList<Traspaso>();
    SqlSession conn=null;
    try{
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Traspaso.getTraspasosByIdRancho", idRancho);
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
    @Path("buscarTraspasos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Traspaso> buscarTraspasos(
            @FormParam("idRancho") String idRancho,
            @FormParam("busqueda") String busqueda){
        List<Traspaso> list = new ArrayList<Traspaso>();
        SqlSession conn = null;
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("busqueda", busqueda);
            
            conn = MyBatisUtil.getSession();
            list = conn.selectList("Traspaso.buscarTraspasos", param);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return list;
    }
    
    @POST
    @Path("registrarTraspaso")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarTraspaso(
            @FormParam("idLoteAnterior") Integer idLoteAnterior,
            @FormParam("idLoteDestino") Integer idLoteDestino, 
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idLoteAnterior", idLoteAnterior);
            param.put("idLoteDestino", idLoteDestino);
            param.put("idRancho", idRancho);
            param.put("idUsuarioAlta", idUsuarioAlta);
            
            conn.insert("Traspaso.registrarTraspaso", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Traspaso registrado correctamente!");
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
    @Path("editarTraspaso")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarTraspaso(
            @FormParam("idTraspaso") Integer idTraspaso,
            @FormParam("idLoteAnterior") Integer idLoteAnterior,
            @FormParam("idLoteDestino") Integer idLoteDestino,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idTraspaso", idTraspaso);
            param.put("idLoteAnterior", idLoteAnterior);
            param.put("idLoteDestino", idLoteDestino);
            param.put("idRancho", idRancho);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Traspaso.editarTraspaso", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Traspaso editado correctamente!");
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
    @Path("cancelarTraspaso")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta cancelarTraspaso(
            @FormParam("idTraspaso") Integer idTraspaso,
            @FormParam("motivoCancelacion") String motivoCancelacion,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idTraspaso", idTraspaso);
            param.put("motivoCancelacion", motivoCancelacion);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Traspaso.cancelarTraspaso", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Traspaso cancelado correctamente!");
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
