package servicios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Movimiento;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("movimiento")
public class MovimientoWS {

    @Context
    private UriInfo context;

    public MovimientoWS() {
    }

    @GET
    @Path("getMovimientosByIdRancho/{idRancho}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movimiento> getMovimientosByIdRancho(@PathParam("idRancho") Integer idRancho){
    List<Movimiento> list = new LinkedList<>();
    SqlSession conn = null;
    try{
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Movimiento.getMovimientosByIdRancho", idRancho);
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
    @Path("buscarMovimientos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movimiento> buscarMovimientos(
            @FormParam("idRancho") String idRancho,
            @FormParam("busqueda") String busqueda){
        List<Movimiento> list = new LinkedList<>();
        SqlSession conn = null;
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("busqueda", busqueda);
            
            conn = MyBatisUtil.getSession();
            list = conn.selectList("Movimiento.buscarMovimientos", param);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return list;
    }
    
    @POST
    @Path("registrarMovimiento")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarMovimiento(
            @FormParam("cantidadVenta") Double cantidadVenta,
            @FormParam("tipo") String tipo,
            @FormParam("idConcepto") Integer idConcepto,
            @FormParam("fecha") String fecha,
            @FormParam("observaciones") String observaciones,
            @FormParam("idRancho") String idRancho,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("cantidadVenta", cantidadVenta);
            param.put("tipo", tipo);
            param.put("idConcepto", idConcepto);
            param.put("fecha", fecha);
            param.put("observaciones", observaciones);
            param.put("idRancho", idRancho);
            param.put("idUsuarioAlta", idUsuarioAlta);
            
            conn.insert("Movimiento.registrarMovimiento", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Movimiento registrado correctamente!");
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
    @Path("editarMovimiento")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarMovimiento(
            @FormParam("idMovimiento") Double idMovimiento,
            @FormParam("cantidadVenta") Double cantidadVenta,
            @FormParam("tipo") String tipo,
            @FormParam("idConcepto") Integer idConcepto,
            @FormParam("fecha") String fecha,
            @FormParam("observaciones") String observaciones,
            @FormParam("idRancho") String idRancho,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idMovimiento", idMovimiento);
            param.put("cantidadVenta", cantidadVenta);
            param.put("tipo", tipo);
            param.put("idConcepto", idConcepto);
            param.put("fecha", fecha);
            param.put("observaciones", observaciones);
            param.put("idRancho", idRancho);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Movimiento.editarMovimiento", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Movimiento editado correctamente!");
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
    @Path("cancelarMovimiento")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta cancelarMovimiento(
            @FormParam("idMovimiento") Double idMovimiento,
            @FormParam("motivoCancelacion") String motivoCancelacion,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idMovimiento", idMovimiento);
            param.put("motivoCancelacion", motivoCancelacion);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Movimiento.cancelarMovimiento", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Movimiento cancelado correctamente!");
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