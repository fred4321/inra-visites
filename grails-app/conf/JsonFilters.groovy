import grails.converters.JSON

class JsonFilters {

    def filters = {
        notPdf(controller:'pdf', invert :true ){
            after = { model ->
                response.setContentType("application/json; charset=utf-8")
                response.setHeader("Access-Control-Allow-Origin", "*")
                response.setHeader("Access-Control-Allow-Headers", "X-Requested-With")
                response.setHeader("Access-Control-Max-Age", "0")
                response.setStatus(model.status)
                if(model.data){
                    render model.data as JSON
                }
                return false			
            }
        }
    }
}
