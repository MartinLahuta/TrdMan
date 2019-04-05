<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TRDMAN</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="col-md-offset-1 col-md-10">
        <h3 class="text-center">TRDMAN</h3>
        <hr />

        <input type="button" value="Read trades"
               onclick="window.location.href='showForm'; return false;"
               class="btn btn-primary" /> <br />
        <br />
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Trade List</div>
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered">
                    <tr>
                        <th>Id</th>
                        <th>ISIN</th>
                        <th>CUR</th>
                        <th>B/S</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Amount</th>
                        <th>Action</th>
                    </tr>

                    <c:if test="${not empty trades}">
                    <!-- loop over and print our customers -->
                    <c:forEach var="trade" items="${trades}">

                        <!-- construct an "update" link with customer id -->
                        <c:url var="updateLink" value="/trade/update">
                            <c:param name="tradeId" value="${trade.trade_id}" />
                        </c:url>

                        <!-- construct an "delete" link with customer id -->
                        <c:url var="deleteLink" value="/trade/delete">
                            <c:param name="tradeId" value="${trade.trade_id}" />
                        </c:url>

                        <tr>
                            <td>${trade.trade_id}</td>
                            <td>${trade.isin}</td>
                            <td>${trade.currency}</td>
                            <td>${trade.buySellTyp}</td>
                            <td>${trade.quantity}</td>
                            <td>${trade.price}</td>
                            <td>${trade.amount}</td>

                            <td>
                                <!-- display the update link --> <a href="${updateLink}">Update</a>
                                | <a href="${deleteLink}"
                                     onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
                            </td>

                        </tr>

                    </c:forEach>
                    </c:if>

                </table>

            </div>
        </div>
    </div>

</div>
<div class="footer">
    <p>Footer</p>
</div>
</body>

</html>
