weex 下 svg 的使用例子如下，更多的信息可以上网查找 svg 相关的内容。

        <svg width="450" height="120" style="position: absolute; left: 39px; top: 20px;">
            <defs>
                <linearGradient id="myLinearGradient1"
                                x1="0%" y1="0%"
                                x2="0%" y2="100%">
                    <stop offset="0%"   stop-color="#FCAF29" opacity="0.99" />
                    <stop offset="15%"   stop-color="#FCA40A" opacity="0.91" />
                    <stop offset="100%" stop-color="#000000" opacity="0.8"/>
                </linearGradient>
            </defs>
            <line x1="0" y1="0" x2="450" y2="0"style="stroke:#ffffff;stroke-width: 1px;border-radius: 0.5px; opacity: 0.1;"></line>
            <line x1="0" y1="35" x2="450" y2="35"style="stroke:#ffffff;stroke-width: 1px;border-radius: 0.5px; opacity: 0.1;"></line>
            <line x1="0" y1="70" x2="450" y2="70"style="stroke:#ffffff;stroke-width: 1px;border-radius: 0.5px; opacity: 0.1;"></line>
            <polygon :points="polylinepointsaa" style="fill:url(#myLinearGradient1);stroke:#000000;stroke-width:0px;" fillOpacity="0.2"/>
            <polyline :points="polylinepoints" style="fill:none;stroke:#e9ae29;stroke-width:2;"/>
        </svg>

多边形，线的点格式格式如下：
            polylinepointsaa : {
                default : function(){
                    return "0,70 200,0 450,70";
                }
            },
            polylinepoints : {
                default : function(){
                    return "0,70 200,0 450,70";
                }
            },


使用的时候需要向 weex 的框架注册 weex 的 控件

            WXSDKEngine.registerComponent("radialGradient", WXSvgRadialGradient.class);
            WXSDKEngine.registerComponent("stop", WXSvgStop.class);
            WXSDKEngine.registerComponent("linearGradient", WXSvgLinearGradient.class);
            WXSDKEngine.registerComponent("defs", WXSvgDefs.class);
            WXSDKEngine.registerComponent("polyline", WXSvgPolyLine.class);
            WXSDKEngine.registerComponent("polygon", WXSvgPolygon.class);
            WXSDKEngine.registerComponent("ellipse", WXSvgEllipse.class);
            WXSDKEngine.registerComponent("rect", WXSvgRect.class);
            WXSDKEngine.registerComponent("line", WXSvgLine.class);
            WXSDKEngine.registerComponent("circle", WXSvgCircle.class);
            WXSDKEngine.registerComponent("path", WXSvgPath.class);
            WXSDKEngine.registerComponent("svg", WXSvgContainer.class);