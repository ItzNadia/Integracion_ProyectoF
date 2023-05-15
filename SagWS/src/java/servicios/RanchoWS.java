package servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Rancho;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("rancho")
public class RanchoWS {

    @Context
    private UriInfo context;

    public RanchoWS() {
    }

    @GET
    @Path("getAllRanchos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rancho> getAllRanchos(){
    List<Rancho> list = new ArrayList<Rancho>();
    SqlSession conn=null;
    try{
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Rancho.getAllRanchos");
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
    @Path("buscarRanchos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rancho> buscarRanchos(
            @FormParam("busqueda") String busqueda){
    List<Rancho> list = new ArrayList<Rancho>();
    SqlSession conn=null;
    try{
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Rancho.buscarRanchos", busqueda);
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
    @Path("registrarRancho")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarRancho(
            @FormParam("nombre") String nombre,
            @FormParam("direccion") String direccion,
            @FormParam("nombreEncargado") String nombreEncargado,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("nombre", nombre);
            param.put("direccion", direccion);
            param.put("nombreEncargado", nombreEncargado);
            param.put("idUsuarioAlta", idUsuarioAlta);
            
            conn.insert("Rancho.registrarRancho", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Rancho registrado correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("No se pudo registrar el rancho...");
        }finally{
            conn.close();
        }
        return res;
    }

    @POST
    @Path("editarRancho")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarRancho(
            @FormParam("idRancho") Integer idRancho,
            @FormParam("nombre") String nombre,
            @FormParam("direccion") String direccion,
            @FormParam("nombreEncargado") String nombreEncargado,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("nombre", nombre);
            param.put("direccion", direccion);
            param.put("nombreEncargado", nombreEncargado);
            param.put("idUsuarioEditor", idUsuarioEditor);

            conn.update("Rancho.editarRancho", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Rancho editado correctamente!");
        }catch(Exception e){
            e.printStackTrace();
            res.setError(true);
            res.setMensaje("Error al editar rancho...");
        }finally{
            conn.close();
        }
        return res;
    }
}