// IMemoryAidlInterface.aidl
package someday.com.test_sharememory;

// Declare any non-default types here with import statements

interface IMemoryAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    ParcelFileDescriptor getParcelFileDescriptor();
}
