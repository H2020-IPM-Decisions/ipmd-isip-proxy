package no.nibio.ipmdisipproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IsipRequest {

    @JsonProperty("variables")
    private Variables variables;

    public IsipRequest() {

    }

    public IsipRequest(double longitude, double latitude, String simulationDate, String crop, String disease) {
        this.variables = new Variables();
        this.variables.crop = new Variable<>(crop);
        this.variables.disease = new Variable<>(disease);
        this.variables.longitude = new Variable<>(longitude);
        this.variables.latitude = new Variable<>(latitude);
        this.variables.simulationDate = new Variable<>(simulationDate);
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public static class Variables {

        private Variable<String> crop;
        private Variable<String> disease;
        private Variable<Double> longitude;
        private Variable<Double> latitude;
        @JsonProperty("simulation_date")
        private Variable<String> simulationDate;
        @JsonProperty("date_time")
        private Variable<List<String>> dateTime;
        @JsonProperty("tot_prec")
        private Variable<List<Double>> totPrec;

        @JsonProperty("t_2m")
        private Variable<List<Double>> t2m;
        @JsonProperty("relhum_2m")
        private Variable<List<Double>> relhum2m;

        public Variable<String> getCrop() {
            return crop;
        }

        public void setCrop(Variable<String> crop) {
            this.crop = crop;
        }

        public Variable<String> getDisease() {
            return disease;
        }

        public void setDisease(Variable<String> disease) {
            this.disease = disease;
        }

        public Variable<Double> getLongitude() {
            return longitude;
        }

        public void setLongitude(Variable<Double> longitude) {
            this.longitude = longitude;
        }

        public Variable<Double> getLatitude() {
            return latitude;
        }

        public void setLatitude(Variable<Double> latitude) {
            this.latitude = latitude;
        }

        public Variable<String> getSimulationDate() {
            return simulationDate;
        }

        public void setSimulationDate(Variable<String> simulationDate) {
            this.simulationDate = simulationDate;
        }

        public Variable<List<String>> getDateTime() {
            return dateTime;
        }

        public void setDateTime(Variable<List<String>> dateTime) {
            this.dateTime = dateTime;
        }

        public void setDateTime(List<String> dateTime) {
            this.dateTime = new Variable<>(dateTime);
        }

        public Variable<List<Double>> getTotPrec() {
            return totPrec;
        }

        public void setTotPrec(Variable<List<Double>> totPrec) {
            this.totPrec = totPrec;
        }

        public void setTotPrec(List<Double> totPrec) {
            this.totPrec = new Variable<>(totPrec);
        }

        public Variable<List<Double>> getT2m() {
            return t2m;
        }

        public void setT2m(Variable<List<Double>> t2m) {
            this.t2m = t2m;
        }

        public void setT2m(List<Double> t2m) {
            this.t2m = new Variable<>(t2m);
        }

        public Variable<List<Double>> getRelhum2m() {
            return relhum2m;
        }

        public void setRelhum2m(Variable<List<Double>> relhum2m) {
            this.relhum2m = relhum2m;
        }

        public void setRelhum2m(List<Double> relhum2m) {
            this.relhum2m = new Variable<>(relhum2m);
        }
    }

    public static class Variable<T> {
        private T data;

        public Variable() {
        }

        public Variable(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
