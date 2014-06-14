<!DOCTYPE html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>BAM Dashboard - Service Statistics</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link href="../resources/css/bootstrap.css" rel="stylesheet">
    <link href="../resources/css/bootstrap-theme.css" rel="stylesheet">
    <link href="../resources/css/bootstrap-missing.css" rel="stylesheet">
    <style>
        body {
            padding-top: 50px;
            padding-bottom: 20px;
        }
    </style>
    <link rel="stylesheet" href="../resources/css/main.css">
    <link rel="stylesheet" href="../resources/font-awesome/css/font-awesome.min.css">
    <script src="../resources/js/vendor/jquery-1.10.1.min.js"></script>
</head>
<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
    your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to
    improve your experience.</p>
<![endif]-->


<!-- Part 1: Wrap all page content here -->
<div id="wrap">
    <div class="navbar navbar-inverse navbar-fixed-top main-menu">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="../../../carbon"><img src="../resources/img/bam-logo.png"/>
                </a>
            </div>
            <div class="navbar-collapse collapse main-menu-inside">
                <ul class="nav navbar-nav pull-left menu1" id="leftnav"></ul>
            </div>
            <!--/.navbar-collapse -->
        </div>
        <div class="breadcrumb-strip">
            <div class="container">
                <!-- Example row of columns -->
                <div class="row">
                    <div class="col-lg-12">

                        <ul class="breadcrumb pull-left">
                            <li><a href="../../../carbon">Carbon Console</a> <span class="divider"></span></li>
                            <li class="active">BPS Statistics</li>
                        </ul>
                        <!--form class="form-search pull-right margin-remover header-form">
                            <input type="text" class="input-medium search-query" placeholder="Activity ID">
                        </form -->

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container content-starter">
        <div class="row">
            <div class="col-lg-12">

            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h1>BPS Statistics</h1>

                <div class="container content-section">


                     <div class="row">
                        <div class="col-lg-12">
                            <div class="navbar timelySwitch pull-left" style="overflow: hidden;">
                                      <div id="process-dd" class="btn-group process-dd-btns">
                                         <button class="btn btn-primary">BPEL Related Stats</button>
                                        <button class="btn">Human Task Related Stats</button>                                   
                                      </div>
                                  </div>
                        </div>
                    </div>

                  
                    <!-- h3 style="background-color:#CAD6E2">Task Related Information</h3 -->

                   

                    <!-- div class="row">
                        <div class="col-lg-12">
                            <div class="well topFilteringPanel"><span class="span3">Select Package Name :
                                      <select id="server-dd" name="basic-combo">
                                          <option value="__default__"></option>
                                             <option>All</option>
                                      </select></span>
                                      
                                
                                <button id="clearSelectionBtn" class="btn btn-primary btn-small filter-btn">Clear
                                </button>
                            </div>
                        </div>
                    </div -->  

                   
                      <div class="row">
                        <div class="col-lg-12">
                            <iframe id="dashboardWidget-4" style="min-height:1200px" src="gadgets/bpel_info.jag?package=All&inst=All&service&opr&t=Now" class="single-column-gadget"></iframe>
                        </div>
                    </div> 

                           <!--/row-->

                    <!-- gadget iframes -->
                  <!--   <div class="row">
                        <div class="col-lg-12">
                            <iframe id="dashboardWidget-1" class="single-column-gadget" src="gadgets/mult-line-dashboard-chart.jag?server=&service&opr&t=Now"></iframe>
                          </div>
                    </div> -->
                    <!-- div class="row">
                        <div class="col-lg-12">
                              <iframe id="dashboardWidget-1" src="gadgets/simple-table.jag?t=Now" class="single-column-gadget"></iframe>
                        </div>
                    </div -->

                    <!-- div class="row">
                        <div class="col-lg-12">
                              <iframe id="dashboardWidget-2" src="gadgets/simple.bar.chart.jag?t=Now" class="single-column-gadget"></iframe>
                        </div>
                    </div -->


                </div>
            </div>

        </div>
        <!-- /container -->
        <div id="push"></div>
    </div>

    <footer id="footer">
        <div class="container">
            <p class="muted credit">&copy; WSO2 2013</p>
        </div>
    </footer>

    <input type="hidden" id="resource_type" value="Endpoint"/>


    <script src="../resources/js/vendor/bootstrap.min.js"></script>
    <script src="../resources/js/vendor/jquery.validate.min.js"></script>
    <script type="text/javascript" src="../resources/js/bamdashboard.js"></script>
    <script type="text/javascript" src="scripts/bam.dashboard.main.js"></script>
    <script type="text/javascript">
        var currentTab = "AS Service Statistics";
    </script>
    <script type="text/javascript" src="../navigation.populator.js"></script>
</body>
</html>