[poul](../../index.md) / [com.sophoun.utils](../index.md) / [ScopeStorageHandler](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ScopeStorageHandler(activity: <ERROR CLASS>)`

A Utility class that help you easy to access android scope storage.
It's require android Q or above.
This class is require when you want to update or delete a file or group of files
using [ContentResolver](#) so [ContentResolver](#) will throw [RecoverableSecurityException](#)
if you doesn't have permission on those files.
So after [ContentResolver](#) throw exception, you can use this call to request those permission
and after user allow or deny it wills callback to [onResult](#) method.
Note:

* To use this class you need to create an instance of it once and use in your activity.
* You must call [handleRequestIntent](handle-request-intent.md) in [onActivityResult](#) to handle callback result.
