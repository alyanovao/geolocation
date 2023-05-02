package ru.aao.geoproxyservice.commons;

public class Constant {
    public static final String NAMEPROJECT = "geoProxyService";						        // название проекта
    public static final String FACADE_ROUTE_ID = NAMEPROJECT + "FacadeRouteId";             // Идентификатор основоного роута

    public static final String LOG_ID = NAMEPROJECT + "LogID"; 					            // Глобальный универсальный идентификатор(GUID)
    public static final String CORRELATION_ID = NAMEPROJECT + "LogCorrelationId";	        // Локальный/частный универсальный идентификатор(GUID)

    public static final String LOG_LEVEL = NAMEPROJECT + "LogLevel";                        // Уровень логирования
    public static final String LOG_COMPONENT = NAMEPROJECT + "LogComponent";		        // Название компонента/модуля
    public static final String LOG_DIRECTION = NAMEPROJECT + "LogDirection";		        // Направление логирования(In/Out/Inner)
    public static final String LOG_STEP = NAMEPROJECT + "LogStep";					        // Название этапа
    public static final String LOG_OPERATION = NAMEPROJECT + "LogOperation";		        // Название операции, выполняемая в компоненте/модуле
    public static final String LOG_REDELIVERY = NAMEPROJECT + "Redelivery";			        // информация для повторных запросов

    public static final String REST_REQUEST = NAMEPROJECT + "RestRequestBody"; 		        // Объект класса для request запроса веб-сервиса
    public static final String REST_RESPONSE = NAMEPROJECT + "RestResponseBody"; 	        // Объект класса для response запроса веб-сервиса
    public static final String ENDPOINT_NAME = "endpointName";
    public static final String SAVE_LOCATION = "saveLocation";
    public static final String GET_LOCATION = "getLocation";
}
