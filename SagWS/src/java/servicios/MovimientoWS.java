package servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.mybatis.MyBatisUtil;
import modelo.pojos.Movimiento;
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
    List<Movimiento> list = new ArrayList<Movimiento>();
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
    @Path("buscarUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movimiento> buscarMovimientos(
            @FormParam("idRancho") String idRancho,
            @FormParam("busqueda") String busqueda){
        List<Movimiento> list = new ArrayList<Movimiento>();
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
}
