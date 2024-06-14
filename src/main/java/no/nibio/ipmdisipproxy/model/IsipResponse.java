package no.nibio.ipmdisipproxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0
 */
public class IsipResponse {
    private Attributes attributes;
    private Nodes nodes;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Nodes getNodes() {
        return nodes;
    }

    public void setNodes(Nodes nodes) {
        this.nodes = nodes;
    }

    public static class Attributes {
        private String id;
        private String description;
        private String model;
        private String source;
        private String path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class Nodes {
        private Result result;

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public static class Result {
            private Attributes attributes;
            private Dimensions dimensions;
            private Map<String, Variable> variables;

            public Attributes getAttributes() {
                return attributes;
            }

            public void setAttributes(Attributes attributes) {
                this.attributes = attributes;
            }

            public Dimensions getDimensions() {
                return dimensions;
            }

            public void setDimensions(Dimensions dimensions) {
                this.dimensions = dimensions;
            }

            public Map<String, Variable> getVariables() {
                return variables;
            }

            public void setVariables(Map<String, Variable> variables) {
                this.variables = variables;
            }

            public static class Attributes {
                private String id;
                private String path;
                @JsonProperty("forecast date")
                private String forecastDate;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public String getForecastDate() {
                    return forecastDate;
                }

                public void setForecastDate(String forecastDate) {
                    this.forecastDate = forecastDate;
                }
            }

            public static class Dimensions {
                private int date;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }
            }

            public static class Variable {
                private Attributes attributes;
                private String type;
                private List<String> shape;

                private List<String> data;

                public Attributes getAttributes() {
                    return attributes;
                }

                public void setAttributes(Attributes attributes) {
                    this.attributes = attributes;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<String> getShape() {
                    return shape;
                }

                public void setShape(List<String> shape) {
                    this.shape = shape;
                }

                public List<String> getData() {
                    return data;
                }

                public void setData(List<String> data) {
                    this.data = data;
                }

                public static class Attributes {
                    private String description;
                    private String units;
                    @JsonProperty("time_zone")
                    private String timeZone;
                    private String interval;
                    private String aggregate;
                    private String link;
                    private String legend;

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getUnits() {
                        return units;
                    }

                    public void setUnits(String units) {
                        this.units = units;
                    }

                    public String getTimeZone() {
                        return timeZone;
                    }

                    public void setTimeZone(String timeZone) {
                        this.timeZone = timeZone;
                    }

                    public String getInterval() {
                        return interval;
                    }

                    public void setInterval(String interval) {
                        this.interval = interval;
                    }

                    public String getAggregate() {
                        return aggregate;
                    }

                    public void setAggregate(String aggregate) {
                        this.aggregate = aggregate;
                    }

                    public String getLink() {
                        return link;
                    }

                    public void setLink(String link) {
                        this.link = link;
                    }

                    public String getLegend() {
                        return legend;
                    }

                    public void setLegend(String legend) {
                        this.legend = legend;
                    }
                }
            }
        }
    }
}
