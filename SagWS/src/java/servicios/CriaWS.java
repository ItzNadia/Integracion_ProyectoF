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
import modelo.pojos.Cria;
import modelo.pojos.Respuesta;
import org.apache.ibatis.session.SqlSession;

@Path("cria")
public class CriaWS {

    @Context
    private UriInfo context;

    public CriaWS() {
    }

    @GET
    @Path("getCriasByIdHatoMadre/{idHatoMadre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cria> getCriasByIdHatoMadre(@PathParam("idHatoMadre") Integer idHatoMadre){
    List<Cria> list = new LinkedList<>();
    SqlSession conn = null;
    try{    
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Cria.getCriasByIdHatoMadre", idHatoMadre);
    }catch(Exception ex){
        ex.printStackTrace();
    }finally{
        if(conn!=null){
            conn.close();
        }
    }
        return list;
    }

    @GET
    @Path("getCriasByIdRancho/{idRancho}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cria> getCriasByIdRancho(@PathParam("idRancho") Integer idRancho){
    List<Cria> list = new LinkedList<>();
    SqlSession conn = null;
    try{    
        conn=MyBatisUtil.getSession();
        list=conn.selectList("Cria.getCriasByIdRancho", idRancho);
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
    @Path("buscarCrias")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cria> buscarCrias(
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idCria") Integer idCria){
        List<Cria> list = new LinkedList<>();
        SqlSession conn = null;
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idRancho", idRancho);
            param.put("idCria", idCria);
            
            conn = MyBatisUtil.getSession();
            list = conn.selectList("Cria.buscarCrias", param);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return list;
    }
    
    @POST
    @Path("registrarCria")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarCria(
            @FormParam("idHatoMadre") Integer idHatoMadre,
            @FormParam("sexo") String sexo,
            @FormParam("fechaNacimiento") String fechaNacimiento,
            @FormParam("idRaza") Integer idRaza,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("observaciones") String observaciones,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioAlta") Integer idUsuarioAlta){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String,Object> param = new HashMap<String, Object>();
            param.put("idHatoMadre", idHatoMadre);
            param.put("sexo", sexo);
            param.put("fechaNacimiento", fechaNacimiento);
            param.put("idRaza", idRaza);
            param.put("idEstatus", idEstatus);
            param.put("observaciones", observaciones);
            param.put("idRancho", idRancho);
            param.put("idUsuarioAlta", idUsuarioAlta);
            
            conn.insert("Cria.registrarCria", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Cría registrada correctamente!");
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
    @Path("editarCria")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarCria(
            @FormParam("idCria") Integer idCria,
            @FormParam("idHatoMadre") Integer idHatoMadre,
            @FormParam("sexo") String sexo,
            @FormParam("fechaNacimiento") String fechaNacimiento,
            @FormParam("idRaza") Integer idRaza,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("observaciones") String observaciones,
            @FormParam("idRancho") Integer idRancho,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor){
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try{
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCria", idCria);
            param.put("idHatoMadre", idHatoMadre);
            param.put("sexo", sexo);
            param.put("fechaNacimiento", fechaNacimiento);
            param.put("idRaza", idRaza);
            param.put("idEstatus", idEstatus);
            param.put("observaciones", observaciones);
            param.put("idRancho", idRancho);
            param.put("idUsuarioEditor", idUsuarioEditor);
            
            conn.update("Cria.editarCria", param);
            conn.commit();
            
            res.setError(false);
            res.setMensaje("¡Cría editada correctamente!");
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
    @Path("editarEstatusCria")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta editarEstatusCria(
            @FormParam("idCria") Integer idCria,
            @FormParam("idEstatus") Integer idEstatus,
            @FormParam("idUsuarioEditor") Integer idUsuarioEditor) {
        Respuesta res = new Respuesta();
        SqlSession conn = MyBatisUtil.getSession();
        try {
            HashMap<String, Object> param = new HashMap<String, Object>();
            param.put("idCria", idCria);
            param.put("idEstatus", idEstatus);
            param.put("idUsuarioEditor", idUsuarioEditor);

            conn.update("Cria.editarEstatusCria", param);
            conn.commit();

            res.setError(false);
            res.setMensaje("¡Estatus de cría editado correctamente!");
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
