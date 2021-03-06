
package com.torodb.torod.db.executor.jobs;

import com.torodb.torod.core.ValueRow;
import com.torodb.torod.core.dbWrapper.DbConnection;
import com.torodb.torod.core.exceptions.ToroException;
import com.torodb.torod.core.exceptions.ToroRuntimeException;
import com.torodb.torod.core.subdocument.values.ScalarValue;
import com.torodb.torod.db.backends.executor.jobs.TransactionalJob;
import java.util.Iterator;

/**
 *
 */
public class SqlSelectCallable extends TransactionalJob<Iterator<ValueRow<ScalarValue<?>>>> {

    private final Report report;
    private final String query;

    public SqlSelectCallable(
            DbConnection connection,
            TransactionAborter abortCallback,
            Report report,
            String query) {
        super(connection, abortCallback);
        this.report = report;
        this.query = query;
    }

    @Override
    protected Iterator<ValueRow<ScalarValue<?>>> failableCall() throws ToroException,
            ToroRuntimeException {

        Iterator<ValueRow<ScalarValue<?>>> result = getConnection().select(query);

        report.sqlSelectExecuted(query);

        return result;
    }

    public static interface Report {
        public void sqlSelectExecuted(String query);
    }
}
